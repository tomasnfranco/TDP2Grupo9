package server

import grails.transaction.Transactional
import static org.springframework.http.HttpStatus.*

@Transactional
class PublicacionService {
    final static def DISTANCIA_MAXIMA = 500
    def notificacionesService

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
            if(params.necesitaTransito)
                necesitaTransito == params.necesitaTransito
            publicador.id != usuario.id
            activa == true
            if(params.tipoPublicacion)
                tipoPublicacion == params.tipoPublicacion
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
        return busqueda
    }

    def misPublicaciones(params) {
        def publicaciones = Publicacion.findAllByPublicador(params.usuario,params)
        publicaciones*.setDistancia(params.usuario.latitud,params.usuario.longitud)
        return publicaciones
    }

    def misPostulaciones(params) {
        def publicaciones = Publicacion.withCriteria(params) {
            quierenAdoptar {
                eq('id',params.usuario.id)
            }
            eq('activa',true)
        }
        publicaciones*.setDistancia(params.usuario.latitud,params.usuario.longitud)
        return publicaciones
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
            if(publicacion.tipoPublicacion == 1)
                notificacionesService.nuevaPostulacion(user.username,publicacion.nombreMascota,publicacion.publicador,publicacion)
            if(publicacion.tipoPublicacion == 2)
                notificacionesService.nuevoPostulacionPerdida(user,publicacion.nombreMascota,publicacion.publicador,publicacion)
            if(publicacion.tipoPublicacion == 3)
                notificacionesService.nuevoPostulacionEncontrada(user,publicacion.nombreMascota,publicacion.publicador,publicacion)
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
            if (!publicacion.quierenAdoptar.id.contains(quiereAdoptar.id)) {
                println "salio porque no existe quiereAdoptar en la lista de los que quieren"
                return FORBIDDEN
            }
            if (!publicacion.publicador.equals(supuestoPublicador)) {
                println "salio porque no es su publicacion"
                return UNAUTHORIZED
            }
            publicacion.concretado = quiereAdoptar
            publicacion.fechaConcretado = new Date()
            publicacion.activa = false //TODO: Ver si con esto esta bien
            publicacion.save(flush: true)
            //Notificaciones
            if (publicacion.tipoPublicacion == 1) {
                notificacionesService.concretarAdopcionElegido(quiereAdoptar, publicacion.nombreMascota, publicacion.publicador,publicacion)
                notificacionesService.concretarAdopcionPublicador(quiereAdoptar, publicacion.nombreMascota, publicacion.publicador)
            }
            if(publicacion.tipoPublicacion == 2){
                notificacionesService.concretarPerdidaElegido(quiereAdoptar, publicacion.nombreMascota, publicacion.publicador,publicacion)
                notificacionesService.concretarPerdidaPublicador(quiereAdoptar, publicacion.nombreMascota, publicacion.publicador)
            }
            if(publicacion.tipoPublicacion == 3){
                notificacionesService.concretarEncontradaElegido(quiereAdoptar, publicacion.nombreMascota, publicacion.publicador,publicacion)
                notificacionesService.concretarEncontradaPublicador(quiereAdoptar, publicacion.nombreMascota, publicacion.publicador)
            }
            if(publicacion.tipoPublicacion == 1) {
                publicacion.quierenAdoptar.each {
                    if (it.id != quiereAdoptar.id) {
                        notificacionesService.concretarAdopcionNoElegido(it, publicacion.nombreMascota, publicacion.publicador)
                    }
                }
            }
            println "salio todo OK"
            return OK
        }
        println "salio porque no existe la publicacion"
        return NOT_FOUND
    }

    def ofrezcoTransito(params){
        Publicacion publicacion = Publicacion.get(params.publicacion)
        Usuario user = params.usuario
        if(publicacion) {
            if(publicacion.ofrecenTransito.contains(user)){
                return METHOD_NOT_ALLOWED
            }
            if(publicacion.publicador.equals(user)){
                return FORBIDDEN
            }
            publicacion.addToOfrecenTransito(user)
            publicacion.save(flush:true)
            notificacionesService.nuevoOfrecimientoTransito(user,publicacion.nombreMascota,publicacion.publicador,publicacion)
            return OK
        }
        return NOT_FOUND
    }

    def cancelarTransito(params){
        Publicacion publicacion = Publicacion.get(params.publicacion)
        Usuario user = params.usuario
        if(publicacion){
            if(publicacion.publicador.equals(user)){
                return FORBIDDEN
            }

            if(!publicacion.ofrecenTransito.contains(user)){
                return METHOD_NOT_ALLOWED
            }

            publicacion.removeFromOfrecenTransito(user)
            publicacion.save(flush:true)
            return OK
        }
        return NOT_FOUND
    }

    def concretarTransito(params){
        Publicacion publicacion = Publicacion.get(params.publicacion)
        println "Publicacion: ${params.publicacion}"
        Usuario supuestoPublicador = params.usuario
        println "Publicador: ${params.usuario}"
        Usuario ofrecioTransito = Usuario.get(params.ofrecioTransito)
        println "Ofrecio transito: ${ofrecioTransito}"
        if(!ofrecioTransito){
            println "salio porque no existe ofrecioTransito"
            return BAD_REQUEST
        }
        if(publicacion) {
            if(!publicacion.ofrecenTransito.contains(ofrecioTransito)){
                println "salio porque no existe ofrecioTransito en la lista de los que ofrecen"
                return FORBIDDEN
            }
            if(!publicacion.publicador.equals(supuestoPublicador)){
                println "salio porque no es su publicacion"
                return UNAUTHORIZED
            }
            publicacion.transito = ofrecioTransito
            publicacion.save(flush:true)
            notificacionesService.concretarTransitoElegido(ofrecioTransito,publicacion.nombreMascota,publicacion.publicador,publicacion)
            notificacionesService.concretarTransitoPublicador(ofrecioTransito,publicacion.nombreMascota,publicacion.publicador)
            publicacion.ofrecenTransito.each {
                if (it.id != ofrecioTransito.id) {
                    notificacionesService.concretarTransitoNoElegido(it, publicacion.nombreMascota, publicacion.publicador)
                }
            }
            println "salio todo OK"
            return OK
        }
        println "salio porque no existe la publicacion"
        return NOT_FOUND
    }

    def misTransitos(params) {
        def publicaciones = Publicacion.withCriteria(params) {
            ofrecenTransito {
                eq('id',params.usuario.id)
            }
            eq('activa',true)
        }
        publicaciones*.setDistancia(params.usuario.latitud,params.usuario.longitud)
        return publicaciones
    }
}
