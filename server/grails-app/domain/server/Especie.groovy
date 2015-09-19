package server
import grails.rest.*

@Resource(uri='/api/especie',formats=['json', 'xml'])
class Especie {
    String tipo
    static constraints = {
    }
}
