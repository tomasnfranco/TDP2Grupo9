package server

import grails.transaction.Transactional

@Transactional
class AlertaService {
    def notificacionesService

    def verificarSiCumpleAlgunaAlerta(Publicacion publicacion) {
        def alertas = Alerta.where{
            color == publicacion.color || color == null
            edad == publicacion.edad || edad == null
            especie == publicacion.especie
            raza == publicacion.raza || raza == null
            sexo == publicacion.sexo || sexo == null
            tamanio == publicacion.tamanio || tamanio == null

            castrado == publicacion.castrado || castrado == null
            compatibleCon == publicacion.compatibleCon || compatibleCon == null
            energia == publicacion.energia || energia == null
            papelesAlDia == publicacion.papelesAlDia || papelesAlDia == null
            proteccion == publicacion.proteccion || proteccion == null
            vacunasAlDia == publicacion.vacunasAlDia || vacunasAlDia == null
            tipoPublicacion == publicacion.tipoPublicacion
        }

        alertas = alertas.list().findAll {
            (publicacion.setDistancia(it.latitud,it.longitud) < it.distancia || it.distancia == 500)
        }

        println "Hay ${alertas.size()} alertas que cumplen con la publicación enviada"

        alertas.each {
            notificacionesService.alertaPublicacionCumple(publicacion.nombreMascota,it.usuario,it)
        }
    }
}
