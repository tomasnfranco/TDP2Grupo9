package server

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class MensajeController {
    static scaffold = true
    def notificacionesService
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE",responder: 'POST']

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Mensaje.list(params), model:[mensajeInstanceCount: Mensaje.count()]
    }

    def show(Mensaje mensajeInstance) {
        respond mensajeInstance
    }

    def create() {
        respond new Mensaje(params)
    }

    @Transactional
    def save(Mensaje mensajeInstance) {
        if (mensajeInstance == null) {
            notFound()
            return
        }
        params.usuarioPregunta = params.usuario
        params.fechaPregunta = new Date()
        params.fechaRespuesta = new Date()
        mensajeInstance = new Mensaje(params)
        if(mensajeInstance.usuarioPregunta.equals(mensajeInstance.publicacion?.publicador)){
            render status:FORBIDDEN
            return
        }
        if (mensajeInstance.hasErrors()) {
            respond mensajeInstance.errors, view:'create'
            return
        }

        mensajeInstance.save flush:true
        notificacionesService.nuevaPregunta(mensajeInstance,mensajeInstance.publicacion.nombreMascota,mensajeInstance.publicacion.publicador)
        request.withFormat {
            html {
                flash.message = message(code: 'default.created.message', args: [message(code: 'mensaje.label', default: 'Mensaje'), mensajeInstance.id])
                redirect mensajeInstance
            }
            '*' { respond mensajeInstance, [status: CREATED] }
        }
    }

    def edit(Mensaje mensajeInstance) {
        respond mensajeInstance
    }

    @Transactional
    def update(Mensaje mensajeInstance) {
        if (mensajeInstance == null) {
            notFound()
            return
        }

        if (mensajeInstance.hasErrors()) {
            respond mensajeInstance.errors, view:'edit'
            return
        }

        mensajeInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Mensaje.label', default: 'Mensaje'), mensajeInstance.id])
                redirect mensajeInstance
            }
            '*'{ respond mensajeInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Mensaje mensajeInstance) {

        if (mensajeInstance == null) {
            notFound()
            return
        }

        mensajeInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Mensaje.label', default: 'Mensaje'), mensajeInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'mensaje.label', default: 'Mensaje'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def responder(){
        def mensaje = Mensaje.get(params.mensaje)
        def respuesta = params.texto
        if(!mensaje){
            println "No existe el mensaje"
            render status: NOT_FOUND
            return
        }
        if(!mensaje.publicacion.publicador.equals(params.usuario)){
            println "Intento responder un mensaje que no es suyo"
            render status: FORBIDDEN
            return
        }
        mensaje.fechaRespuesta = new Date()
        mensaje.respuesta = respuesta
        mensaje.save flush: true
        notificacionesService.respuestaAPregunta(mensaje,mensaje.publicacion.nombreMascota,mensaje.publicacion.publicador)
        render status: OK
    }
}
