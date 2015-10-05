package server
import grails.rest.*

@Resource(uri='/api/vacunasAlDia',formats=['json', 'xml'])
class VacunasAlDia {
    String tipo
    boolean porDefecto = false
    static constraints = {
    }
}
