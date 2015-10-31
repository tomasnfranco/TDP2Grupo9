package server

import grails.transaction.Transactional

@Transactional
class NotificacionesService {
    def androidGcmService
    def mailService
    def pie = "<br/><br/>"
    def logoApp = "<br/><img width='114px' height='114px' src='https://raw.githubusercontent.com/tomasnfranco/TDP2Grupo9/master/app/src/main/res/drawable/logo_aplicacion.png'/>"

    def nuevaPostulacion(def postulante, def mascota, Usuario publicador) {
        if (publicador.email && !publicador.email.empty) {
            mailService.sendMail {
                async true
                to "${publicador.email}"
                subject "[BUSCA SUS HUELLAS]: Solicitud de Adopción de $mascota"
                html "<html><body>Hola ${publicador.username},<br/> <b>$postulante</b> se postuló para adoptar a <b><em>$mascota</em></b> " +
                        "<br/><br/>Entra a BUSCA SUS HUELLAS y concreta esta adopción. ${logoApp}</body></html>"
            }
            println "E-mail enviado al usuario ${publicador.username} al mail ${publicador.email} porque $postulante se postulo por $mascota"
        } else {
            println "No se envio mail por postulación al usuario que habia publicado ${publicador.username} debido que no tiene el mail registrado en el sistema."
        }
        if (publicador.gcmId != null && publicador.gcmId != '') {
            try {
                androidGcmService.sendMessage([message: "$postulante se postuló para adoptar a $mascota"], [publicador.gcmId])
            } catch (Exception e) {
                println "No se pudo mandar la push $e"
            }
        }
    }

    def concretarAdopcionElegido(Usuario postulante, def mascota, Usuario publicador){
        if(postulante.email && !postulante.email.empty) {
            mailService.sendMail {
                async true
                to "${postulante.email}"
                subject "[BUSCA SUS HUELLAS]: Felicidades, sos el dueño de $mascota!"
                html "<html><body>Hola ${postulante.username},<br/> <b>${publicador.username}</b> te ha seleccionado para ser el nuevo dueño de <b><em>$mascota</em></b> " +
                        "<br/>Estos son los datos de ${publicador.username} para que lo contactes y puedan coordinar la adopción:<br/>" +
                        "Email: ${publicador.email}<br/>" +
                        "Teléfono: ${publicador.telefono}<br/>" +
                        "<br/><br/>" +
                        "BUSCA SUS HUELLAS está feliz por haber encontrado un hogar a <b>$mascota</b>${logoApp}</body></html>"
            }
            println "E-mail enviado al usuario ${postulante.username} al mail ${postulante.email} porque ${publicador.username} concreto por $mascota"
        } else {
            println "No se envio mail por adopción exitosa al usuario que quedó ${postulante.username} debido que no tiene el mail registrado en el sistema."
        }
        if (publicador.gcmId != null && publicador.gcmId != '') {
            try {
                androidGcmService.sendMessage([message: "${publicador.username} te ha seleccionado para ser el nuevo dueño de $mascota"], [publicador.gcmId])
            } catch (Exception e) {
                println "No se pudo mandar la push $e"
            }
        }
    }

    def concretarAdopcionNoElegido(Usuario postulante, def mascota, Usuario publicador){
        if(postulante.email && !postulante.email.empty) {
            mailService.sendMail {
                async true
                to "${postulante.email}"
                subject "[BUSCA SUS HUELLAS]: $mascota ha encontrado un hogar"
                html "<html><body><b>${publicador.username}</b> ha decidido que entregará a <b><em>$mascota</em></b> a otro usuario," +
                        " seguí buscando y dentro de poco vas a encontrar esa mascota que queres." +
                        "<br/><br/>Atentamente,<br/>" +
                        "El Equipo de BUSCA SUS HUELLAS ${logoApp}</body></html>"
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
                        "BUSCA SUS HUELLAS está feliz por haber encontrado un hogar a <b>$mascota</b>${logoApp}</body></html>"
            }
            println "E-mail enviado al usuario ${publicador.username} al mail ${publicador.email} porque decidio que ${postulante.username} se quede con $mascota"
        } else {
            println "No se envio mail por adopción exitosa al publicador ${publicador.username} debido que no tiene el mail registrado en el sistema."
        }
    }

    def publicacionCancelada(Publicacion publicacion, Usuario postulante){
        if(postulante.email && !postulante.email.empty) {
            mailService.sendMail {
                async true
                to "${postulante.email}"
                subject "[BUSCA SUS HUELLAS]: Se cancelo la publicacion de $publicacion.nombreMascota"
                html "<html><body><b>${publicacion.publicador.username}</b> ha decidido cancelar la publicación de <b><em>$publicacion.nombreMascota</em></b>," +
                        " seguí buscando y dentro de poco vas a encontrar esa mascota que queres." +
                        "<br/><br/>Atentamente,<br/>" +
                        "El Equipo de BUSCA SUS HUELLAS ${logoApp}</body></html>"
            }
            println "E-mail enviado al usuario ${postulante.username} al mail ${postulante.email} porque ${publicacion.publicador.username} cancelo la publicacion $publicacion"
        } else {
            println "No se envio mail por cancelación de adopcion al usuario que se habia postulado ${postulante.username} debido que no tiene el mail registrado en el sistema."
        }

        if (postulante.gcmId != null && postulante.gcmId != '') {
            try {
                androidGcmService.sendMessage([message: "${publicacion.publicador.username} ha decidido cancelar la publicación de $publicacion.nombreMascota"], [postulante.gcmId])
            } catch (Exception e) {
                println "No se pudo mandar la push $e"
            }
        }
    }

    def nuevaPregunta(Mensaje mensaje, def mascota, Usuario publicador){
        if(publicador.email && !publicador.email.empty) {
            mailService.sendMail {
                async true
                to "${publicador.email}"
                subject "[BUSCA SUS HUELLAS]: Preguntaron por $mascota"
                html "<html><body>Hola ${publicador.username},<br/>" +
                        "${mensaje.usuarioPregunta.username} ha preguntado:" +
                        "<br/><img src='http://i61.tinypic.com/212egw0.jpg'/> ${mensaje.texto}" +
                        "<br/><br/>" +
                        "Entra a BUSCA SUS HUELLAS para responderle ${logoApp}</body></html>"
            }
            println "E-mail enviado al usuario ${publicador.username} al mail ${publicador.email} porque ${mensaje.usuarioPregunta.username} pregunto por $mascota"
        } else {
            println "No se envio mail por pregunta al publicador ${publicador.username} debido que no tiene el mail registrado en el sistema."
        }
        if (publicador.gcmId != null && publicador.gcmId != '') {
            try {
                androidGcmService.sendMessage([message: "${mensaje.usuarioPregunta.username} ha preguntado por $mascota: ${mensaje.texto}"], [publicador.gcmId])
            } catch (Exception e) {
                println "No se pudo mandar la push $e"
            }
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
                        "<br/><img src='http://i61.tinypic.com/212egw0.jpg'/> ${mensaje.texto}" +
                        "<br/><span style='padding-left:5px;'><img src='http://i61.tinypic.com/2h84mz5.jpg'/>${mensaje.respuesta}</span>"+
                        "<br/><br/>" +
                        "Entra a BUSCA SUS HUELLAS para realizar otra consulta por $mascota ${logoApp}</body></html>"
            }
            println "E-mail enviado al usuario ${mensaje.usuarioPregunta.username} al mail ${mensaje.usuarioPregunta.email} porque ${publicador.username} respondio por $mascota"
        } else {
            println "No se envio mail por pregunta al publicador ${mensaje.usuarioPregunta.username} debido que no tiene el mail registrado en el sistema."
        }
        if (mensaje.usuarioPregunta.gcmId != null && mensaje.usuarioPregunta.gcmId != '') {
            try {
                androidGcmService.sendMessage([message: "${publicador.username} respondió tu consulta sobre $mascota: ${mensaje.respuesta}"], [mensaje.usuarioPregunta.gcmId])
            } catch (Exception e) {
                println "No se pudo mandar la push $e"
            }
        }
    }

    def alertaPublicacionCumple(def mascota, Usuario usuario){
        if(usuario.email && !usuario.email.empty) {
            mailService.sendMail {
                async true
                to "${usuario.email}"
                subject "[BUSCA SUS HUELLAS]: Han Publicado una mascota que cumple tus requisitos"
                html "<html><body>Hola ${usuario.username},<br/>" +
                        "Recientemente publicaron a <b><em>$mascota</em></b> que cumple tus requisitos de búsqueda." +
                        "<br/><br/>Entra a BUSCA SUS HUELLAS y en la sección Mis Alertas podes ver la publicación ${logoApp}</body></html>"
            }
            println "E-mail enviado al usuario ${usuario.username} al mail ${usuario.email} por alerta , mascota: $mascota"
        } else {
            println "No se envio mail por adopción exitosa al publicador ${usuario.username} debido que no tiene el mail registrado en el sistema."
        }
        if (usuario.gcmId != null && usuario.gcmId != '') {
            try {
                androidGcmService.sendMessage([message: "Publicaron a $mascota que cumple tus requisitos de búsqueda"], [usuario.gcmId])
            } catch (Exception e) {
                println "No se pudo mandar la push $e"
            }
        }
    }

    def nuevoOfrecimientoTransito(def postulante, def mascota, Usuario publicador){
        if(publicador.email && !publicador.email.empty) {
            mailService.sendMail {
                async true
                to "${publicador.email}"
                subject "[BUSCA SUS HUELLAS]: Solicitud de Tránsito para $mascota"
                html "<html><body>Hola ${publicador.username},<br/> <b>$postulante</b> se ofreció para dar un hogar de tránsito a <b><em>$mascota</em></b> " +
                        "<br/><br/>Entra a BUSCA SUS HUELLAS y acepta este ofrecimiento. ${logoApp}</body></html>"
            }
            println "E-mail enviado al usuario ${publicador.username} al mail ${publicador.email} porque $postulante se postulo por $mascota"
        } else {
            println "No se envio mail por postulación al usuario que habia publicado ${publicador.username} debido que no tiene el mail registrado en el sistema."
        }
        if (publicador.gcmId != null && publicador.gcmId != '') {
            try {
                androidGcmService.sendMessage([message: "$postulante se ofreció para dar un hogar de tránsito a $mascota"], [publicador.gcmId])
            } catch (Exception e) {
                println "No se pudo mandar la push $e"
            }
        }
    }

    def concretarTransitoElegido(Usuario postulante, def mascota, Usuario publicador){
        if(postulante.email && !postulante.email.empty) {
            mailService.sendMail {
                async true
                to "${postulante.email}"
                subject "[BUSCA SUS HUELLAS]: $mascota! se quedará con vos hasta que lo adopten"
                html "<html><body>Hola ${postulante.username},<br/> <b>${publicador.username}</b> te ha seleccionado para darle hogar de tránsito a <b><em>$mascota</em></b> " +
                        "<br/>Estos son los datos de ${publicador.username} para que lo contactes y puedan coordinar:<br/>" +
                        "Email: ${publicador.email}<br/>" +
                        "Teléfono: ${publicador.telefono}<br/>" +
                        "<br/><br/>" +
                        "BUSCA SUS HUELLAS está feliz de que estés ayudando con la búsqueda de un hogar a <b>$mascota</b>${logoApp}</body></html>"
            }
            println "E-mail enviado al usuario ${postulante.username} al mail ${postulante.email} porque ${publicador.username} concreto por $mascota"
        } else {
            println "No se envio mail por adopción exitosa al usuario que quedó ${postulante.username} debido que no tiene el mail registrado en el sistema."
        }
        if (publicador.gcmId != null && publicador.gcmId != '') {
            try {
                androidGcmService.sendMessage([message: "${publicador.username} te ha seleccionado para darle hogar de tránsito a $mascota"], [publicador.gcmId])
            } catch (Exception e) {
                println "No se pudo mandar la push $e"
            }
        }
    }

    def concretarTransitoNoElegido(Usuario postulante, def mascota, Usuario publicador){
        if(postulante.email && !postulante.email.empty) {
            mailService.sendMail {
                async true
                to "${postulante.email}"
                subject "[BUSCA SUS HUELLAS]: $mascota tendrá otro hogar de tránsito"
                html "<html><body><b>${publicador.username}</b>  ha decidido que otro usuario dará hogar de tránsito a <b><em>$mascota</em></b><br/>" +
                        "De todas maneras de parte de BUSCA SUS HUELLAS agradecemos tu ayuda para encontrar un hogar a <b>$mascota</b>." +
                        "<br/><br/>Atentamente,<br/>" +
                        "El Equipo de BUSCA SUS HUELLAS ${logoApp}</body></html>"
            }
            println "E-mail enviado al usuario ${postulante.username} al mail ${postulante.email} porque ${publicador.username} eligio a otro por $mascota"
        } else {
            println "No se envio mail por adopción no exitosa al usuario que NO quedó ${postulante.username} debido que no tiene el mail registrado en el sistema."
        }
    }

    def concretarTransitoPublicador(Usuario postulante, def mascota, Usuario publicador){
        if(publicador.email && !publicador.email.empty) {
            mailService.sendMail {
                async true
                to "${publicador.email}"
                subject "[BUSCA SUS HUELLAS]: Has seleccionado un hogar de tránsito para $mascota!"
                html "<html><body>Hola ${publicador.username},<br/>" +
                        "Recientemente has seleccionado un hogar de tránsito para <b><em>$mascota</em></b>. " +
                        "<br/>Estos son los datos de ${postulante.username} para que lo contactes y puedan coordinar:<br/>" +
                        "Email: ${postulante.email}<br/>" +
                        "Teléfono: ${postulante.telefono}<br/>" +
                        "<br/><br/>" +
                        "BUSCA SUS HUELLAS está feliz por haber encontrado un hogar de tránsito a <b>$mascota</b>${logoApp}</body></html>"
            }
            println "E-mail enviado al usuario ${publicador.username} al mail ${publicador.email} porque decidio que ${postulante.username} se quede con $mascota"
        } else {
            println "No se envio mail por adopción exitosa al publicador ${publicador.username} debido que no tiene el mail registrado en el sistema."
        }
    }

    //Para publicaciones de Pedidas
    def nuevoPostulacionPerdida(def postulante, def mascota, Usuario publicador){
        if(publicador.email && !publicador.email.empty) {
            mailService.sendMail {
                async true
                to "${publicador.email}"
                subject "[BUSCA SUS HUELLAS]: Alguien cree que encontró a $mascota"
                html "<html><body>Hola ${publicador.username},<br/> <b>$postulante</b> cree tener a <b><em>$mascota</em></b> " +
                        "<br/><br/>Entra a BUSCA SUS HUELLAS para ver tu búsqueda. ${logoApp}</body></html>"
            }
            println "E-mail enviado al usuario ${publicador.username} al mail ${publicador.email} porque $postulante se postulo por $mascota"
        } else {
            println "No se envio mail por postulación al usuario que habia publicado ${publicador.username} debido que no tiene el mail registrado en el sistema."
        }
        if (publicador.gcmId != null && publicador.gcmId != '') {
            try {
                androidGcmService.sendMessage([message: "$postulante cree tener a $mascota"], [publicador.gcmId])
            } catch (Exception e) {
                println "No se pudo mandar la push $e"
            }
        }
    }

    def concretarPerdidaElegido(Usuario postulante, def mascota, Usuario publicador){
        if(postulante.email && !postulante.email.empty) {
            mailService.sendMail {
                async true
                to "${postulante.email}"
                subject "[BUSCA SUS HUELLAS]: Gracias por ayudar a encontrar a $mascota!"
                html "<html><body>Hola ${postulante.username},<br/> <b>${publicador.username}</b> ha seleccionado que tenes a <b><em>$mascota</em></b> " +
                        "<br/>Estos son los datos de ${publicador.username} para que lo contactes y puedan coordinar:<br/>" +
                        "Email: ${publicador.email}<br/>" +
                        "Teléfono: ${publicador.telefono}<br/>" +
                        "<br/><br/>" +
                        "BUSCA SUS HUELLAS está feliz por haber encontrado a <b>$mascota</b>${logoApp}</body></html>"
            }
            println "E-mail enviado al usuario ${postulante.username} al mail ${postulante.email} porque ${publicador.username} concreto por $mascota"
        } else {
            println "No se envio mail por adopción exitosa al usuario que quedó ${postulante.username} debido que no tiene el mail registrado en el sistema."
        }
        if (publicador.gcmId != null && publicador.gcmId != '') {
            try {
                androidGcmService.sendMessage([message: "${publicador.username} ha seleccionado que tenes a $mascota"], [publicador.gcmId])
            } catch (Exception e) {
                println "No se pudo mandar la push $e"
            }
        }
    }

    def concretarPerdidaPublicador(Usuario postulante, def mascota, Usuario publicador){
        if(publicador.email && !publicador.email.empty) {
            mailService.sendMail {
                async true
                to "${publicador.email}"
                subject "[BUSCA SUS HUELLAS]: Has encontrado a $mascota!"
                html "<html><body>Hola ${publicador.username},<br/>" +
                        "Recientemente has seleccionado que encontraron a <b><em>$mascota</em></b>. " +
                        "<br/>Estos son los datos de ${postulante.username} para que lo contactes y puedan coordinar:<br/>" +
                        "Email: ${postulante.email}<br/>" +
                        "Teléfono: ${postulante.telefono}<br/>" +
                        "<br/><br/>" +
                        "BUSCA SUS HUELLAS está feliz por haber encontrado a <b>$mascota</b>${logoApp}</body></html>"
            }
            println "E-mail enviado al usuario ${publicador.username} al mail ${publicador.email} porque decidio que ${postulante.username} se quede con $mascota"
        } else {
            println "No se envio mail por adopción exitosa al publicador ${publicador.username} debido que no tiene el mail registrado en el sistema."
        }
    }

    //Para publicaciones de Encontradas
    def nuevoPostulacionEncontrada(def postulante, def mascota, Usuario publicador){
        if(publicador.email && !publicador.email.empty) {
            mailService.sendMail {
                async true
                to "${publicador.email}"
                subject "[BUSCA SUS HUELLAS]: Alguien dice ser el dueño de $mascota"
                html "<html><body>Hola ${publicador.username},<br/> <b>$postulante</b> dice ser el dueño de <b><em>$mascota</em></b> " +
                        "<br/><br/>Entra a BUSCA SUS HUELLAS ver tu publicación y encontrar el dueño de $mascota ${logoApp}</body></html>"
            }
            println "E-mail enviado al usuario ${publicador.username} al mail ${publicador.email} porque $postulante se postulo por $mascota"
        } else {
            println "No se envio mail por postulación al usuario que habia publicado ${publicador.username} debido que no tiene el mail registrado en el sistema."
        }
        if (publicador.gcmId != null && publicador.gcmId != '') {
            try {
                androidGcmService.sendMessage([message: "$postulante dice ser el dueño de $mascota"], [publicador.gcmId])
            } catch (Exception e) {
                println "No se pudo mandar la push $e"
            }
        }
    }

    def concretarEncontradaElegido(Usuario postulante, def mascota, Usuario publicador){
        if(postulante.email && !postulante.email.empty) {
            mailService.sendMail {
                async true
                to "${postulante.email}"
                subject "[BUSCA SUS HUELLAS]: Felicidades encontraste a $mascota!"
                html "<html><body>Hola ${postulante.username},<br/> <b>${publicador.username}</b> ha seleccionado que <b><em>$mascota</em></b> te pertenece" +
                        "<br/>Estos son los datos de ${publicador.username} para que lo contactes y puedan coordinar:<br/>" +
                        "Email: ${publicador.email}<br/>" +
                        "Teléfono: ${publicador.telefono}<br/>" +
                        "<br/><br/>" +
                        "BUSCA SUS HUELLAS está feliz por haber encontrado a <b>$mascota</b>${logoApp}</body></html>"
            }
            println "E-mail enviado al usuario ${postulante.username} al mail ${postulante.email} porque ${publicador.username} concreto por $mascota"
        } else {
            println "No se envio mail por adopción exitosa al usuario que quedó ${postulante.username} debido que no tiene el mail registrado en el sistema."
        }
        if (publicador.gcmId != null && publicador.gcmId != '') {
            try {
                androidGcmService.sendMessage([message: "${publicador.username} ha seleccionado que $mascota te pertenece"], [publicador.gcmId])
            } catch (Exception e) {
                println "No se pudo mandar la push $e"
            }
        }
    }

    def concretarEncontradaPublicador(Usuario postulante, def mascota, Usuario publicador){
        if(publicador.email && !publicador.email.empty) {
            mailService.sendMail {
                async true
                to "${publicador.email}"
                subject "[BUSCA SUS HUELLAS]: Has encontrado el dueño de $mascota!"
                html "<html><body>Hola ${publicador.username},<br/>" +
                        "Recientemente has seleccionado que $postulante.username es el dueño de <b><em>$mascota</em></b>. " +
                        "<br/>Estos son los datos de ${postulante.username} para que lo contactes y puedan coordinar la devolución:<br/>" +
                        "Email: ${postulante.email}<br/>" +
                        "Teléfono: ${postulante.telefono}<br/>" +
                        "<br/><br/>" +
                        "BUSCA SUS HUELLAS está feliz por haber encontrado el dueño de <b>$mascota</b>${logoApp}</body></html>"
            }
            println "E-mail enviado al usuario ${publicador.username} al mail ${publicador.email} porque decidio que ${postulante.username} se quede con $mascota"
        } else {
            println "No se envio mail por adopción exitosa al publicador ${publicador.username} debido que no tiene el mail registrado en el sistema."
        }
    }
}