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

        if (usuarioInstance.hasErrors()) {
            respond usuarioInstance.errors, view:'create'
            return
        }
		usuarioInstance.activo = true;
		usuarioInstance.generarToken()
        usuarioInstance.save flush:true
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
	
	def login = {
		println "Login $params"
		if(params.facebookId != null){
			def user = Usuario.findByFacebookId(params.facebookId)
			if(user != null ){
				if(user.activo){
					user.generarToken()
					user.save(flush:true)
					respond user
				} else {
					render status: 401
				}
			} else {
				notFound()
			}
		} else {
			respond status:NOT_FOUND
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
        render status:200
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
}
