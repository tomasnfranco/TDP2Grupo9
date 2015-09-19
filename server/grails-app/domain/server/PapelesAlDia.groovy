package server
import grails.rest.*

@Resource(uri='/api/papelesAlDia',formats=['json', 'xml'])
class PapelesAlDia {
    String tipo
    static constraints = {
    }
}
