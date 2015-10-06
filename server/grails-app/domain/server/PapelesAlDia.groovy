package server
import grails.rest.*

@Resource(uri='/api/papelesAlDia',formats=['json', 'xml'])
class PapelesAlDia {
    String tipo
    boolean porDefecto = false
    static constraints = {
    }
}
