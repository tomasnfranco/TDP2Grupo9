package server
import grails.rest.*

@Resource(uri='/api/edad',formats=['json', 'xml'])
class Edad {
    String nombre
    static constraints = {
    }
}
