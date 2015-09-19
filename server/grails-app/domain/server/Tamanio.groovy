package server
import grails.rest.*

@Resource(uri='/api/tamanio',formats=['json', 'xml'])
class Tamanio {
    String tipo
    static constraints = {
    }
}
