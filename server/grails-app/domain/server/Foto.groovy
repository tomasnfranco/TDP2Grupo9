package server
import grails.rest.*

class Foto {
    String base64

    static belongsTo = [publicacion : Publicacion]
	
    static mapping = {
        base64 type: 'text'
    }

    String toString(){
        return base64
    }
}
