package server
import grails.rest.*

@Resource(uri='/api/color',formats=['json', 'xml'])
class Color {
    String nombre
    static constraints = {
    }
}
