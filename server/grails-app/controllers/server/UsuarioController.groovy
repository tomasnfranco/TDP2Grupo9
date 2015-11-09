package server

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

class UsuarioController {
	static scaffold = true
	static allowedMethods= [index:'GET',show:'GET',save:'POST',update:['POST','PUT'],delete:'DELETE',login:'POST', logout:['POST','PUT','DELETE']]
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Usuario.list(params), model:[usuarioInstanceCount: Usuario.count()]
    }

    def show(Usuario usuarioInstance) {
        respond usuarioInstance
    }

    def create() {
        respond new Usuario(params)
    }

    @Transactional
    def save(Usuario usuarioInstance) {
        if (usuarioInstance == null) {
            notFound()
            return
        }
        println "Parametros normales: $params"
        println "Parametros Json: $params"

        if (usuarioInstance.hasErrors()) {
            if (usuarioInstance.hasErrors()) {
                println "usuarioInstance tiene errores"
                usuarioInstance = new Usuario(request.getJSON())
                println "usuarioInstance tiene el json ahora"
                usuarioInstance.validate()
                if(usuarioInstance.hasErrors()) {
                    println "usuarioInstance con el Json sigue teniendo errores"
                    println usuarioInstance.errors
                    respond usuarioInstance.errors, view:'create'
                    return
                }
            }

        }
        println "Usuario no tiene errores"

        if(params.longitud)
            usuarioInstance.longitud = Double.parseDouble(params.longitud)
        if(params.latitud)
            usuarioInstance.latitud = Double.parseDouble(params.latitud)
        usuarioInstance.activo = true;
		usuarioInstance.generarToken()
        usuarioInstance.save flush:true
        println "Usuario guardado correctamente"

        if(usuarioInstance.username?.isEmpty()){
            usuarioInstance.username = 'NEWBIE' + usuarioInstance.id
            usuarioInstance.save flush:true
        }

        request.withFormat {
            html {
                flash.message = message(code: 'default.created.message', args: [message(code: 'usuario.label', default: 'Usuario'), usuarioInstance.id])
                redirect usuarioInstance
            }
            '*' { respond usuarioInstance, [status: CREATED] }
        }
    }

    def edit(Usuario usuarioInstance) {
        respond usuarioInstance
    }

    @Transactional
    def update(Usuario usuarioInstance) {
		println usuarioInstance
        if (usuarioInstance == null) {
            notFound()
            return
        }

        if (usuarioInstance.hasErrors()) {
            respond usuarioInstance.errors, view:'edit'
            return
        }

        usuarioInstance.save flush:true

        request.withFormat {
            html {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Usuario.label', default: 'Usuario'), usuarioInstance.id])
                redirect usuarioInstance
            }
            '*'{ respond usuarioInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Usuario usuarioInstance) {

        if (usuarioInstance == null) {
            notFound()
            return
        }

        usuarioInstance.delete flush:true

        request.withFormat {
            html {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Usuario.label', default: 'Usuario'), usuarioInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }
	
	def login() {
		println "Login $params"
        def user = null
		if(params.facebookId != null){
            println "Encontro usuario de facebook"
			user = Usuario.findByFacebookId(params.facebookId)
		} else if(params.email != null) {
            user = Usuario.findByEmail(params.email)
            if(params.password == null ||params.password.trim() == ''){
                println "no se envio contraseña"
                render status: FORBIDDEN
                return
            } else {
                if(user != null) {
                    println "se encontro usuario por mail"
                    if (!user.password.equals(params.password)) {
                        println "las contraseñas no coinciden"
                        render status: METHOD_NOT_ALLOWED
                        user = null
                        return
                    }
                }
            }
        } else {
			notFound()
            return
		}
        if(user != null ){
            if(user.activo){
                user.generarToken()
                user.save(flush:true)
                respond user
                return
            } else {
                render status: UNAUTHORIZED
                return
            }
        } else {
            notFound()
        }
	}

    def logout = {
        println "Logout $params"
        def user = Usuario.findByToken(params.token)
        if (user == null) {
            notFound()
            return
        }
        user.token = ''
        user.save flush:true
        render status:OK
    }

    def registrarGCM(){
        if(params.gcmToken != null && params.gcmToken != ''){
            Usuario usuario = params.usuario
            usuario.gcmId = params.gcmToken
            usuario.save(flush:true)
            render status: OK
        } else {
            render status: BAD_REQUEST
        }

    }

    protected void notFound() {
        request.withFormat {
            html {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'usuario.label', default: 'Usuario'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def administrar(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def lista = Usuario.list(params).sort(){it.id}
        def publicacionesSize = [:]
        def publicacionesConDenuncias = [:]
        lista.each {
            def publicaciones = Publicacion.findAllByPublicador(it)
            publicacionesSize[it.id] = publicaciones.size()
            println "Usuario: ${it.id}"
            println publicacionesSize[it.id]
            publicacionesConDenuncias[it.id] =publicaciones.inject(0) {result, p -> result + Denuncia.countByPublicacion(p)}
            println publicacionesConDenuncias[it.id]
        }

        return [lista:lista, usuarioInstanceCount: Usuario.count(),publicacionesSize:publicacionesSize,denuncias:publicacionesConDenuncias]
    }

    def bloquear(Usuario usuario){
        usuario.activo = false
        usuario.save(flush:true)
        flash.message = "Usuario ${usuario.username} bloqueado."
        redirect(action:'administrar',controller:'usuario')
    }

    def desbloquear(Usuario usuario){
        usuario.activo = true
        usuario.save(flush:true)
        flash.message = "Se reactivo al usuario ${usuario.username}."
        redirect(action:'administrar',controller:'usuario')
    }

    def publicaciones(Usuario usuario){
        render usuario
    }
}
