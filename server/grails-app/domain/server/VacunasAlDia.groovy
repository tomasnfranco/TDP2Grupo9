package server
import grails.rest.*

@Resource(uri='/api/vacunasAlDia',formats=['json', 'xml'])
class VacunasAlDia {
    String tipo
    static constraints = {
    }
}
