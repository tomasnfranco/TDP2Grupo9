package server

import grails.transaction.Transactional

@Transactional
class NotificacionesService {

    def mailService
    def pie = "<br/><br/>"

    def nuevaPostulacion(def postulante, def mascota, Usuario publicador){
        if(publicador.email && !publicador.email.empty) {
            mailService.sendMail {
                async true
                to "${publicador.email}"
                subject "[BUSCA SUS HUELLAS]: Solicitud de Adopción de $mascota"
                html "<html><body>Hola ${publicador.username},<br/> <b>$postulante</b> se postuló para adoptar a <b><em>$mascota</em></b> " +
                        '<br/><br/>Entra a BUSCA SUS HUELLAS y concreta esta adopción. </body></html>'
            }
            println "E-mail enviado al usuario ${publicador.username} al mail ${publicador.email} porque $postulante se postulo por $mascota"
        } else {
            println "No se envio mail por postulación al usuario que habia publicado ${publicador.username} debido que no tiene el mail registrado en el sistema."
        }
    }

    def concretarAdopcionElegido(Usuario postulante, def mascota, Usuario publicador){
        if(postulante.email && !postulante.email.empty) {
            mailService.sendMail {
                async true
                to "${postulante.email}"
                subject "[BUSCA SUS HUELLAS]: Felicidades, $mascota es tuyo!"
                html "<html><body>Hola ${postulante.username},<br/> <b>${publicador.username}</b> ha decidido que te entregará a <b><em>$mascota</em></b> " +
                        "<br/>Estos son los datos de ${publicador.username} para que lo contactes y puedan coordinar la adopción:<br/>" +
                        "Email: ${publicador.email}<br/>" +
                        "Teléfono: ${publicador.telefono}<br/>" +
                        "<br/><br/>" +
                        "BUSCA SUS HUELLAS está feliz por haber encontrado un hogar a <b>$mascota</b></body></html>"
            }
            println "E-mail enviado al usuario ${postulante.username} al mail ${postulante.email} porque ${publicador.username} concreto por $mascota"
        } else {
            println "No se envio mail por adopción exitosa al usuario que quedó ${postulante.username} debido que no tiene el mail registrado en el sistema."
        }
    }

    def concretarAdopcionNoElegido(Usuario postulante, def mascota, Usuario publicador){
        if(postulante.email && !postulante.email.empty) {
            mailService.sendMail {
                async true
                to "${postulante.email}"
                subject "[BUSCA SUS HUELLAS]: $mascota ha encontrado otro hogar"
                html "<html><body><b>${publicador.username}</b> ha decidido que entregará a <b><em>$mascota</em></b> a otro usuario," +
                        " seguí buscando y dentro de poco vas a encontrar esa mascota que queres." +
                        "<br/><br/>Atentamente,<br/>" +
                        "El Equipo de BUSCA SUS HUELLAS</body></html>"
            }
            println "E-mail enviado al usuario ${postulante.username} al mail ${postulante.email} porque ${publicador.username} eligio a otro por $mascota"
        } else {
            println "No se envio mail por adopción no exitosa al usuario que NO quedó ${postulante.username} debido que no tiene el mail registrado en el sistema."
        }
    }

    def concretarAdopcionPublicador(Usuario postulante, def mascota, Usuario publicador){
        if(publicador.email && !publicador.email.empty) {
            mailService.sendMail {
                async true
                to "${publicador.email}"
                subject "[BUSCA SUS HUELLAS]: Has encontrado un hogar para $mascota!"
                html "<html><body>Hola ${publicador.username},<br/>" +
                        "Recientemente has concretado la adopción de <b><em>$mascota</em></b>. " +
                        "<br/>Estos son los datos de ${postulante.username} para que lo contactes y puedan coordinar la adopción:<br/>" +
                        "Email: ${postulante.email}<br/>" +
                        "Teléfono: ${postulante.telefono}<br/>" +
                        "<br/><br/>" +
                        "BUSCA SUS HUELLAS está feliz por haber encontrado un hogar a <b>$mascota</b></body></html>"
            }
            println "E-mail enviado al usuario ${publicador.username} al mail ${publicador.email} porque decidio que ${postulante.username} se quede con $mascota"
        } else {
            println "No se envio mail por adopción exitosa al publicador ${publicador.username} debido que no tiene el mail registrado en el sistema."
        }
    }

    def nuevaPregunta(Mensaje mensaje, def mascota, Usuario publicador){
        if(publicador.email && !publicador.email.empty) {
            mailService.sendMail {
                async true
                to "${publicador.email}"
                subject "[BUSCA SUS HUELLAS]: Preguntaron por $mascota"
                html "<html><body>Hola ${publicador.username},<br/>" +
                        "${mensaje.usuarioPregunta.username} ha preguntado" +
                        "<br/><img src='https://photos-4.dropbox.com/t/2/AAD-g3fUvh2c0MuTeX0r6uJxHmztfDhAotfQlIhupi4NOA/12/114874040/jpeg/32x32/1/1444496400/0/2/Pregunta.gif/CLit4zYgASACIAMgBiAHKAEoAigH/tWAphwZclRnYLCy62smJsp9AVDcTy8HdqBglS_gb044?size_mode=5'/> ${mensaje.texto}" +
                        "<br/><br/>" +
                        "Entra a BUSCA SUS HUELLAS para responderle y conseguirle un hogar a $mascota</body></html>"
            }
            println "E-mail enviado al usuario ${publicador.username} al mail ${publicador.email} porque ${mensaje.usuarioPregunta.username} pregunto por $mascota"
        } else {
            println "No se envio mail por pregunta al publicador ${publicador.username} debido que no tiene el mail registrado en el sistema."
        }
    }

    def respuestaAPregunta(Mensaje mensaje, def mascota, Usuario publicador){
        if(mensaje.usuarioPregunta.email && !mensaje.usuarioPregunta.email.empty) {
            mailService.sendMail {
                async true
                to "${mensaje.usuarioPregunta.email}"
                subject "[BUSCA SUS HUELLAS]: Respondieron tu consulta por $mascota"
                html "<html><body>Hola ${mensaje.usuarioPregunta.username},<br/>" +
                        "${publicador.username} respondió tu consulta" +
                        "<br/><img src='https://photos-4.dropbox.com/t/2/AAD-g3fUvh2c0MuTeX0r6uJxHmztfDhAotfQlIhupi4NOA/12/114874040/jpeg/32x32/1/1444496400/0/2/Pregunta.gif/CLit4zYgASACIAMgBiAHKAEoAigH/tWAphwZclRnYLCy62smJsp9AVDcTy8HdqBglS_gb044?size_mode=5'/> ${mensaje.texto}" +
                        "<br/><span style='padding-left:5px;'><img src='https://photos-1.dropbox.com/t/2/AAAWNx_KV7oyZKasURm0P6uQVphIAtyTyhcv34xHOcycsQ/12/114874040/jpeg/32x32/1/1444496400/0/2/respuesta.gif/CLit4zYgASACIAMgBiAHKAEoAigH/R9Kk5f6VHxU_LfFSslcTyMR1b7yTR7DVzC5_Zvvx_IA?size_mode=5'/>${mensaje.respuesta}</span>"+
                        "<br/><br/>" +
                        "Entra a BUSCA SUS HUELLAS para realizar otra consulta o adoptar a $mascota</body></html>"
            }
            println "E-mail enviado al usuario ${mensaje.usuarioPregunta.username} al mail ${mensaje.usuarioPregunta.email} porque ${publicador.username} respondio por $mascota"
        } else {
            println "No se envio mail por pregunta al publicador ${mensaje.usuarioPregunta.username} debido que no tiene el mail registrado en el sistema."
        }
    }

    def alertaPublicacionCumple(def mascota, Usuario usuario){
        if(usuario.email && !usuario.email.empty) {
            mailService.sendMail {
                async true
                to "${usuario.email}"
                subject "[BUSCA SUS HUELLAS]: Han Publicado una mascota que cumple tus requisitos"
                html "<html><body>Hola ${usuario.username},<br/>" +
                        "Recientemente publicaron a  <b><em>$mascota</em></b> que cumple tus requisitos de búsqueda." +
                        "<br/><br/>Entra a BUSCA SUS HUELLAS y en la sección Mis Alertas podes ver la publicación</body></html>"
            }
            println "E-mail enviado al usuario ${usuario.username} al mail ${usuario.email} por alerta , mascota: $mascota"
        } else {
            println "No se envio mail por adopción exitosa al publicador ${usuario.username} debido que no tiene el mail registrado en el sistema."
        }
    }

}
