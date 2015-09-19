package server
import grails.rest.*

@Resource(uri='/api/sexo',formats=['json', 'xml'])
class Sexo {
    String tipo
    static constraints = {
    }
}
