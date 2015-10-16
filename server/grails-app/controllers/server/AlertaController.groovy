package server

import grails.converters.JSON
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class AlertaController {
    static scaffold = true
    static allowedMethods = [save: "POST", update: "PUT", delete: ["DELETE","POST"]]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Alerta.list(params), model:[alertaInstanceCount: Alerta.count()]
    }

    def show(Alerta alertaInstance) {
        respond alertaInstance
    }

    def create() {
        respond new Alerta(params)
    }

    @Transactional
    def save(Alerta alertaInstance) {
        if (alertaInstance == null) {
            notFound()
            return
        }

        if (alertaInstance.hasErrors()) {
            respond alertaInstance.errors, view:'create'
            return
        }

        alertaInstance.save flush:true

        if(params.nombre == null || params.nombre.empty){
            alertaInstance.nombre = "Alerta${alertaInstance.id}"
        }

        if(params.latitud == null)
            alertaInstance.latitud = params.usuario.latitud
        if(params.longitud == null)
            alertaInstance.longitud = params.usuario.longitud

        request.withFormat {
            html {
                flash.message = message(code: 'default.created.message', args: [message(code: 'alerta.label', default: 'Alerta'), alertaInstance.id])
                redirect alertaInstance
            }
            '*' { respond alertaInstance, [status: CREATED] }
        }
    }

    def edit(Alerta alertaInstance) {
        respond alertaInstance
    }

    @Transactional
    def update(Alerta alertaInstance) {
        if (alertaInstance == null) {
            notFound()
            return
        }

        if (alertaInstance.hasErrors()) {
            respond alertaInstance.errors, view:'edit'
            return
        }

        alertaInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Alerta.label', default: 'Alerta'), alertaInstance.id])
                redirect alertaInstance
            }
            '*'{ respond alertaInstance, [status: OK] }
        }
    }

    def delete(Alerta alertaInstance) {

        if (alertaInstance == null) {
            notFound()
            return
        }
        if(alertaInstance.id == null)
            alertaInstance = Alerta.get(params.alerta)

        alertaInstance.delete flush:true

        request.withFormat {
            html {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Alerta.label', default: 'Alerta'), alertaInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'alerta.label', default: 'Alerta'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def misAlertas(){
        def alertas = Alerta.findAllByUsuario(params.usuario,params)
        render alertas as JSON
    }
}