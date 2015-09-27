package server

import grails.converters.JSON

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.rest.RestfulController

@Transactional(readOnly = true)
class PublicacionController extends RestfulController<Publicacion>  {
    static scaffold = true
    def publicacionService
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", atributos:'GET']

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Publicacion.list(params), model:[publicacionInstanceCount: Publicacion.count()]
    }

    def show(Publicacion publicacionInstance) {
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

        publicacionInstance.activa = true;
        publicacionInstance.fechaPublicacion = new Date()

        if (publicacionInstance.hasErrors()) {
            respond publicacionInstance.errors, view:'create'
            return
        }

        publicacionInstance.save flush:true

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

        if (publicacionInstance.hasErrors()) {
            respond publicacionInstance.errors, view:'edit'
            return
        }

        publicacionInstance.save flush:true

        request.withFormat {
            form multipartForm {
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

        publicacionInstance.delete flush:true

        request.withFormat {
            form multipartForm {
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
}
