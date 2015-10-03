package server

import grails.transaction.Transactional

@Transactional
class PublicacionService {
    final static def DISTANCIA_MAXIMA = 10

    //TODO: Cambiar distancia maxima por parametro configurable por el administrador del sistema
    def buscar(def params, def usuario) {
        def busqueda = Publicacion.findAll(params){
            if(params.castrado)
                castrado.id == params.castrado
            if(params.color)
                color.id == params.color
            if(params.compatibleCon)
                compatibleCon.id == params.compatibleCon
            if(params.edad)
                edad.id == params.edad
            if(params.energia)
                energia.id == params.energia
            if(params.especie)
                especie.id == params.especie
            if(params.papelesAlDia)
                papelesAlDia.id == params.papelesAlDia
            if(params.proteccion)
                proteccion.id == params.proteccion
            if(params.sexo)
                sexo.id == params.sexo
            if(params.tamanio)
                tamanio.id == params.tamanio
            if(params.vacunasAlDia)
                vacunasAlDia.id == params.vacunasAlDia
            publicador.id != usuario.id
            activa == true
            if(params.tipoPublicacion)
                tipoPublicacion == params.tipoPublicacion
            else
                tipoPublicacion == 1
        }
        double latitud = params.latitud ? Double.parseDouble(params.latitud) : usuario.latitud
        double longitud = params.longitud ? Double.parseDouble(params.longitud) : usuario.longitud
        busqueda*.setDistancia(latitud,longitud)
        busqueda = busqueda.findAll{it.distancia <= (params.distancia ? Double.parseDouble(params.distancia) : DISTANCIA_MAXIMA)}
        if(params.orden == null || params.orden == 'distancia')
            busqueda = busqueda.sort{it.distancia}
        else if (params.orden == 'fechaAsc')
            busqueda = busqueda.sort{it.fechaPublicacion}
        else if (params.orden == 'fechaDesc') {
            busqueda = busqueda.sort { it.fechaPublicacion }
            busqueda.reverse(true)
        }
        return busqueda.collect{[id: it.id,
                                 publicador: it.publicador.username,
                                 distancia: it.distancia,
                                 foto: it.fotos ? it.fotos[0].base64 : '',
                                 necesitaTransito: it.necesitaTransito,
                                 nombreMascota : it.nombreMascota,
                                 requiereCuidadosEspeciales : it.requiereCuidadosEspeciales,
                                 fecha : it.fechaPublicacion]}
    }

    def misPublicaciones(params) {
        //TODO: Definir que atributos devolver de la publicacion
        def publicaciones = Publicacion.findAllByPublicador(params.usuario)
        return publicaciones.collect{[id: it.id,
                                      foto: it.fotos ? it.fotos[0].base64 : '',
                                      necesitaTransito: it.necesitaTransito,
                                      nombreMascota : it.nombreMascota,
                                      requiereCuidadosEspeciales : it.requiereCuidadosEspeciales,
                                      fecha : it.fechaPublicacion]}
    }

    def misAdopciones(params) {
        //TODO: Definir que atributos devolver de la publicacion
        def publicaciones = Publicacion.findAllByConcretado(params.usuario)
        return publicaciones.collect{[id: it.id,
                                      foto: it.fotos ? it.fotos[0].base64 : '',
                                      nombreMascota : it.nombreMascota]}
    }
}
