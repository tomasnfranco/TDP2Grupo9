package server

class Mensaje {
    Publicacion publicacion
    String pregunta
    String respuesta = ''
    Usuario usuarioOrigen
    Usuario usuarioDestino
    Date fechaPregunta = new Date()
    Date fechaRespuesta

    static constraints = {
        pregunta blank: false
        respuesta blank:true
        usuarioOrigen nullable: true
        usuarioDestino nullable: true
    }

    static marshalling = {
        virtual {
            usuarioOrigenNombre {value,json -> json.value(value.usuarioOrigen?.username ?: '')}
            usuarioOrigenId {value,json -> json.value(value.usuarioOrigen?.id ?: null)}
            usuarioDestinoNombre {value,json -> json.value(value.usuarioDestino?.username ?: '')}
            usuarioDestinoId {value,json -> json.value(value.usuarioDestino?.id ?: null)}
        }
    }
}
