package server
import grails.rest.*

@Resource(uri='/api/proteccion',formats=['json', 'xml'])
class Proteccion {
    String tipo
    static constraints = {
    }
}
