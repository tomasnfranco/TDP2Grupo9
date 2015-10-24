package server

import grails.transaction.Transactional
import static org.springframework.http.HttpStatus.*

@Transactional
class PublicacionService {
    final static def DISTANCIA_MAXIMA = 50
    def notificacionesService

    //TODO: Cambiar distancia maxima por parametro configurable por el administrador del sistema
    def buscar(def params, def usuario) {
		println params
		println params.raza
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
			if(params.raza)
                raza.id == params.raza
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
                                 publicadorNombre: it.publicador.username,
                                 publicadorId: it.publicador.id,
                                 distancia: it.distancia,
                                 foto: it.fotos ? it.fotos[0].base64 : '',
                                 necesitaTransito: it.necesitaTransito,
                                 nombreMascota : it.nombreMascota,
                                 requiereCuidadosEspeciales : it.requiereCuidadosEspeciales,
                                 condiciones: it.condiciones ? it.condiciones.trim() : '',
                                 fecha : it.fechaPublicacion]}
    }

    def misPublicaciones(params) {
        def publicaciones = Publicacion.findAllByPublicador(params.usuario,params)
        publicaciones*.setDistancia(params.usuario.latitud,params.usuario.longitud)
        return publicaciones.collect{[id: it.id,
                                      publicadorNombre: it.publicador.username,
                                      publicadorId: it.publicador.id,
                                      distancia: it.distancia,
                                      foto: it.fotos ? it.fotos[0].base64 : '',
                                      necesitaTransito: it.necesitaTransito,
                                      nombreMascota : it.nombreMascota,
                                      requiereCuidadosEspeciales : it.requiereCuidadosEspeciales,
                                      condiciones: it.condiciones ? it.condiciones.trim() : '',
                                      fecha : it.fechaPublicacion]}
    }

    def misPostulaciones(params) {
        def publicaciones = Publicacion.withCriteria(params) {
            quierenAdoptar {
                eq('id',params.usuario.id)
            }
            eq('activa',true)
        }
        publicaciones*.setDistancia(params.usuario.latitud,params.usuario.longitud)
        return publicaciones.collect{[id: it.id,
                                      publicadorNombre: it.publicador.username,
                                      publicadorId: it.publicador.id,
                                      distancia: it.distancia,
                                      foto: it.fotos ? it.fotos[0].base64 : '',
                                      necesitaTransito: it.necesitaTransito,
                                      nombreMascota : it.nombreMascota,
                                      requiereCuidadosEspeciales : it.requiereCuidadosEspeciales,
                                      condiciones: it.condiciones ? it.condiciones.trim() : '',
                                      fecha : it.fechaPublicacion]}
    }

    def quieroAdoptar(params){
        Publicacion publicacion = Publicacion.get(params.publicacion)
        Usuario user = params.usuario
        if(publicacion) {
            if(publicacion.quierenAdoptar.contains(user)){
                return METHOD_NOT_ALLOWED
            }
            if(publicacion.publicador.equals(user)){
                return FORBIDDEN
            }
            publicacion.addToQuierenAdoptar(user)
            publicacion.save(flush:true)
            notificacionesService.nuevaPostulacion(user.username,publicacion.nombreMascota,publicacion.publicador);
            return OK
        }
        return NOT_FOUND
    }

    def cancelarPostulacion(params){
        Publicacion publicacion = Publicacion.get(params.publicacion)
        Usuario user = params.usuario
        if(publicacion){
            if(publicacion.publicador.equals(user)){
                return FORBIDDEN
            }

            if(!publicacion.quierenAdoptar.contains(user)){
                return METHOD_NOT_ALLOWED
            }

            publicacion.removeFromQuierenAdoptar(user)
            publicacion.save(flush:true)
            return OK
        }
        return NOT_FOUND
    }

    def concretarAdopcion(params){
        Publicacion publicacion = Publicacion.get(params.publicacion)
        println "Publicacion: ${params.publicacion}"
        Usuario supuestoPublicador = params.usuario
        println "Publicador: ${params.usuario}"
        Usuario quiereAdoptar = Usuario.get(params.quiereAdoptar)
        println "Quiere adoptar: ${quiereAdoptar}"
        if(!quiereAdoptar){
            println "salio porque no existe quiereAdoptar"
            return BAD_REQUEST
        }
        if(publicacion) {
            if(!publicacion.quierenAdoptar.contains(quiereAdoptar)){
                println "salio porque no existe quiereAdoptar en la lista de los que quieren"
                return FORBIDDEN
            }
            if(!publicacion.publicador.equals(supuestoPublicador)){
                println "salio porque no es su publicacion"
                return UNAUTHORIZED
            }
            publicacion.concretado = quiereAdoptar
            publicacion.activa = false //TODO: Ver si con esto esta bien
            publicacion.save(flush:true)
            //Notificaciones
            notificacionesService.concretarAdopcionElegido(quiereAdoptar,publicacion.nombreMascota,publicacion.publicador)
            notificacionesService.concretarAdopcionPublicador(quiereAdoptar,publicacion.nombreMascota,publicacion.publicador)
            publicacion.quierenAdoptar.each {
                if(it.id != quiereAdoptar.id){
                    notificacionesService.concretarAdopcionNoElegido(it,publicacion.nombreMascota,publicacion.publicador)
                }
            }
            println "salio todo OK"
            return OK
        }
        println "salio porque no existe la publicacion"
        return NOT_FOUND
    }
}
