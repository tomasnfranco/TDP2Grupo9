package server

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class MensajeController {
    static scaffold = true

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

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
        mensajeInstance = new Mensaje(params)

        if (mensajeInstance.hasErrors()) {
            respond mensajeInstance.errors, view:'create'
            return
        }

        mensajeInstance.save flush:true

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
}
