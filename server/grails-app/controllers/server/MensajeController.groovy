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

    @Transactional
    def save(Mensaje mensajeInstance) {
        if (mensajeInstance == null) {
            notFound()
            return
        }
        params.usuarioOrigen = params.usuario
        params.fechaPregunta = new Date()
        params.fechaRespuesta = new Date()
        mensajeInstance = new Mensaje(params)

        if (mensajeInstance.hasErrors()) {
            respond mensajeInstance.errors, view:'create'
            return
        }

        mensajeInstance.save flush:true
        notificacionesService.nuevaPregunta(mensajeInstance,mensajeInstance.publicacion.nombreMascota)
        request.withFormat {
            html {
                flash.message = message(code: 'default.created.message', args: [message(code: 'mensaje.label', default: 'Mensaje'), mensajeInstance.id])
                redirect mensajeInstance
            }
            '*' { respond mensajeInstance, [status: CREATED] }
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
        def respuesta = params.texto ?: params.respuesta
        if(!mensaje){
            println "No existe el mensaje"
            render status: NOT_FOUND
            return
        }
        mensaje.fechaRespuesta = new Date()
        mensaje.respuesta = respuesta
        mensaje.save flush: true
        notificacionesService.respuestaAPregunta(mensaje,mensaje.publicacion.nombreMascota)
        render status: OK
    }
}
