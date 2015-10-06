package server
import grails.rest.*

@Resource(uri='/api/energia',formats=['json', 'xml'])
class Energia {
    String tipo
    boolean porDefecto = false
    static constraints = {
    }
}
