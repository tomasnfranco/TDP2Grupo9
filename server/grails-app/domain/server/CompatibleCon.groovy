package server
import grails.rest.*

@Resource(uri='/api/compatibleCon',formats=['json', 'xml'])
class CompatibleCon {
    String compatibleCon
    static constraints = {
    }
}
