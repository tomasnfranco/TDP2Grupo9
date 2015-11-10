package server

class Denuncia {
    String motivo
    Date fecha = new Date()
    Publicacion publicacion
    Usuario denunciante
    String descripcion

    static constraints = {
    }
}
