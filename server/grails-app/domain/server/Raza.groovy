package server
import grails.rest.*

@Resource(uri='/api/raza',formats=['json', 'xml'])
class Raza {
    String nombre
    Especie especie
    static constraints = {
    }
}
