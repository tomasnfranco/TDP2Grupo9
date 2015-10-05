package server
import grails.rest.*

@Resource(uri='/api/castrado',formats=['json', 'xml'])
class Castrado {
    String tipo
    boolean porDefecto = false
    static constraints = {
    }
}
