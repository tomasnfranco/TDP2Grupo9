package server

import grails.converters.JSON
import groovy.time.TimeCategory
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.rest.RestfulController

@Transactional(readOnly = true)
class PublicacionController extends RestfulController<Publicacion>  {
    static scaffold = true
    def publicacionService
    def alertaService
    def notificacionesService
    static allowedMethods = [save: "POST", update: ["PUT","POST"], delete: ["DELETE","POST"],
                             atributos:'GET',quieroAdoptar: 'POST',concretarAdopcion:'POST',mensajes:'GET',
                             ofrezcoTransito: "POST", cancelarTransito: "POST",concretarTransito:"POST"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Publicacion.list(params), model:[publicacionInstanceCount: Publicacion.count()]
    }

    def show(Publicacion publicacionInstance) {
        publicacionInstance.setDistancia(params.usuario.latitud,params.usuario.longitud)
        respond publicacionInstance
    }

    def ver(Publicacion publicacionInstance){
        respond publicacionInstance
    }

    def create() {
        respond new Publicacion(params)
    }

    @Transactional
    def save(Publicacion publicacionInstance) {
        if (publicacionInstance == null) {
            notFound()
            return
        }
		publicacionInstance = new Publicacion(params)
		
        if(params.usuario)
            publicacionInstance.publicador = params.usuario
        if(!params.castrado)
            publicacionInstance.castrado = Castrado.findByPorDefecto(true)
        if(!params.compatibleCon)
            publicacionInstance.compatibleCon = CompatibleCon.findByPorDefecto(true)
        if(!params.energia)
            publicacionInstance.energia = Energia.findByPorDefecto(true)
        if(!params.papelesAlDia)
            publicacionInstance.papelesAlDia = PapelesAlDia.findByPorDefecto(true)
        if(!params.proteccion)
            publicacionInstance.proteccion = Proteccion.findByPorDefecto(true)
        if(!params.vacunasAlDia)
            publicacionInstance.vacunasAlDia = VacunasAlDia.findByPorDefecto(true)
        if(params.latitud == null)
            publicacionInstance.latitud = params.usuario.latitud
        else
            publicacionInstance.latitud = Double.parseDouble(params.latitud)
        if(params.longitud == null)
            publicacionInstance.longitud = params.usuario.longitud
        else
            publicacionInstance.longitud = Double.parseDouble(params.longitud)

        publicacionInstance.activa = true;
        publicacionInstance.fechaPublicacion = new Date()

        if (publicacionInstance.hasErrors()) {
            respond publicacionInstance.errors, view:'create'
            return
        }

        publicacionInstance.save flush:true
        if(alertaService)
            alertaService.verificarSiCumpleAlgunaAlerta(publicacionInstance)

        request.withFormat {
            html {
                flash.message = message(code: 'default.created.message', args: [message(code: 'publicacion.label', default: 'Publicacion'), publicacionInstance.id])
                redirect publicacionInstance
            }
            '*' { respond publicacionInstance, [status: CREATED] }
        }
    }

    def edit(Publicacion publicacionInstance) {
        respond publicacionInstance
    }

    @Transactional
    def update(Publicacion publicacionInstance) {
        if (publicacionInstance == null) {
            notFound()
            return
        }

        if(publicacionInstance.id == null) {
            publicacionInstance = Publicacion.get(params.publicacion)
            publicacionInstance.properties = params
            if(params.latitud)
                publicacionInstance.latitud = Double.parseDouble(params.latitud)
            if(params.longitud)
                publicacionInstance.longitud = Double.parseDouble(params.longitud)
        }

        if (publicacionInstance.hasErrors()) {
            respond publicacionInstance.errors, view:'edit'
            return
        }

        publicacionInstance.save flush:true

        request.withFormat {
            html {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Publicacion.label', default: 'Publicacion'), publicacionInstance.id])
                redirect publicacionInstance
            }
            '*'{ respond publicacionInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Publicacion publicacionInstance) {
        if (publicacionInstance == null) {
            notFound()
            return
        }

        if(publicacionInstance.id == null)
            publicacionInstance = Publicacion.get(params.publicacion)

        //Notifico a los usuarios que querian adoptar que la publicacion fue cancelada
        publicacionInstance.quierenAdoptar.each{
            notificacionesService.publicacionCancelada(publicacionInstance, it)
        }

        publicacionInstance.delete flush:true

        request.withFormat {
            html {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Publicacion.label', default: 'Publicacion'), publicacionInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    def buscar(){
            render  publicacionService.buscar(params,params.usuario) as JSON
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'publicacion.label', default: 'Publicacion'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def atributos(){
        def respuesta = [:]
        respuesta.castrado = Castrado.list()
        respuesta.color = Color.list()
        respuesta.compatibleCon = CompatibleCon.list()
        respuesta.edad = Edad.list()
        respuesta.energia = Energia.list()
        respuesta.especie = Especie.list()
        respuesta.papelesAlDia = PapelesAlDia.list()
        respuesta.proteccion = Proteccion.list()
        respuesta.raza = Raza.list()
        respuesta.sexo = Sexo.list()
        respuesta.tamanio = Tamanio.list()
        respuesta.vacunasAlDia = VacunasAlDia.list()
        render respuesta as JSON
    }

    def misPublicaciones(){
        render publicacionService.misPublicaciones(params) as JSON
    }

    def misPostulaciones(){
        render publicacionService.misPostulaciones(params) as JSON
    }

    def quieroAdoptar(){
        render status: publicacionService.quieroAdoptar(params)
    }


    def cancelarPostulacion(){
        render status: publicacionService.cancelarPostulacion(params)
    }

    def concretarAdopcion(){
        println params
        render status: publicacionService.concretarAdopcion(params)
    }

    def misTransitos(){
        render publicacionService.misTransitos(params) as JSON
    }

    def ofrezcoTransito(){
        render status: publicacionService.ofrezcoTransito(params)
    }

    def cancelarTransito(){
        render status: publicacionService.cancelarTransito(params)
    }

    def concretarTransito(){
        println params
        render status: publicacionService.concretarTransito(params)
    }

    def mensajes(){
        Publicacion publicacion = Publicacion.get(params.publicacion)
        render publicacion.preguntas.sort{it.fechaPregunta} as JSON
    }

    def denunciar(){
        Denuncia denuncia = new Denuncia(params)
        denuncia.fecha = new Date()
        denuncia.denunciante = params.usuario
        denuncia.save(flush:true)
        render status:OK
    }

    def reporte(){
        Date desde = params.desde ?: (new Date() - 30)
        println "Desde: $desde"
        Date hasta = params.hasta ?: new Date()
        use( TimeCategory ) {
            hasta += 59.minutes
            hasta += 59.seconds
            hasta += 23.hours
        }
        println "Hasta: $hasta"
        List<Publicacion> publicaciones = Publicacion.withCriteria () {
            or {
                and {
                    between("fechaPublicacion", desde, hasta)
                    isNull("fechaConcretado")
                }
                or {
                    between("fechaPublicacion", desde, hasta)
                    between("fechaConcretado", desde, hasta)
                }
            }
        }
        println "${params.especie}"
        if(params.especie && params.especie != null && params.especie != '-1'){
            int especie = params.especie.toInteger()
            println "Especie ${especie}"
            publicaciones = publicaciones.findAll(){
                                it.especie.id == especie
                            }

        }
        println "Publicaciones encontradas: ${publicaciones.size()}"
        List<Publicacion> enAdopcion = publicaciones.findAll(){
            it.tipoPublicacion == 1 && it.concretado == null
        }
        List<Publicacion> adoptadas = publicaciones.findAll(){
            it.tipoPublicacion == 1 && it.concretado != null
        }

        int encontradasSinReclamar = publicaciones.findAll(){
            it.tipoPublicacion == 3 && it.concretado == null
        }.size()

        int perdidasReclamadas = publicaciones.findAll(){
            it.tipoPublicacion == 2 && it.concretado != null
        }.size()

        int perdidasSinReclamar = publicaciones.findAll(){
            it.tipoPublicacion == 2 && it.concretado == null
        }.size()

        int encontradas = encontradasSinReclamar + perdidasReclamadas
        int perdidas = encontradasSinReclamar + perdidasSinReclamar
        println "Encontradas $encontradas"
        println "Perdidas $perdidas"
        println "EnAdopcion ${enAdopcion.size()}"
        println "Adoptadas ${adoptadas.size()}"
        return [perdidas:perdidas,encontradas:encontradas,enAdopcion:enAdopcion.size(),adoptadas:adoptadas.size(),especies : Especie.list(), desde: desde, hasta:hasta]
    }
}
