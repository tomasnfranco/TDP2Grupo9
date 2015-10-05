package server

class Mensaje {
    Publicacion publicacion
    String texto
    String respuesta = ''
    Usuario usuarioPregunta

    static constraints = {
        texto blank: false
        respuesta blank:true
    }

    static marshalling = {
        virtual {
            usuarioPreguntaNombre {value,json -> json.value(value.usuarioPregunta?.username ?: '')}
        }
    }
}
