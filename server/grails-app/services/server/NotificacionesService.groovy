package server

import grails.transaction.Transactional

@Transactional
class NotificacionesService {

    def mailService

    def nuevaPostulacion(def postulante, def mascota, Usuario publicador){
        if(publicador.email && !publicador.email.empty) {
            mailService.sendMail {
                async true
                to "${publicador.email}"
                subject "[ENCONTRA A TU MEJOR AMIGO]: Solicitud de Adopción de $mascota"
                html "<html><body>Hola ${publicador.username},<br/> <b>$postulante</b> se postulo para adoptar a <b><em>$mascota</em></b> " +
                        ' </body></html>'
            }
            println "E-mail enviado al usuario ${publicador.username} al mail ${publicador.email} porque $postulante se postulo por $mascota"
        } else {
            println "No se envio mail por postulación al usuario que habia publicado ${publicador.username} debido que no tiene el mail registrado en el sistema."
        }
    }
}
