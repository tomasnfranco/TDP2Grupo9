package server

import grails.transaction.Transactional

@Transactional
class PublicacionService {

    def buscar(def params, def usuario) {
        def busqueda = Publicacion.findAll(){
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
        }
        def listado = busqueda
        listado.each {it.setDistancia(usuario.latitud,usuario.longitud)}
        listado.each{println it.distancia}
        listado = listado.sort{it.distancia}.collect{[id: it.id,publicador: it.publicador.username,distancia: it.distancia,foto: it.fotos ? it.fotos[0].base64 : '',necesitaTransito: it.necesitaTransito,
		nombreMascota : it.nombreMascota, requiereCuidadosEspeciales : it.requiereCuidadosEspeciales]}
        return listado
    }
}
