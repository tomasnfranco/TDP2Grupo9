package server
import grails.rest.*

@Resource(uri='/api/foto',formats=['json', 'xml','html'])
class Foto {
    String base64

    static belongsTo = [publicacion : Publicacion]

    static constraints = {
        base64 maxSize: 100000
    }

    String toString(){
        return base64
    }
}
