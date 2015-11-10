package server

import com.google.android.gcm.server.Message

class HelloController {
	def androidGcmService
    def index() { 
		render  "{Hello World!}"
	}
	
	def list = {
		render  "{Hello World!}"
	}

	def enviarMensaje(){
		String token = params.gcmToken
		androidGcmService.sendMessage([
				data : 'The message value for the key aMessageKey',
				anotherMessageKey : 'The message value for the key anotherMessageKey',
				message: 'Hola Romi, te llego?'
		], [token])
		render "Enviado Ok a $params.gcmToken"
	}
	
	def enviarNotificacion(){
		androidGcmService.sendMessage([
				data : 'The message value for the key aMessageKey',
				tipo_id : params.tipo_id,
				id : params.id,
				message: params.message,
				token : params.token
		], [params.gcmToken])
		render "Enviado Ok a $params.gcmToken"
	}

	def login() {
		println "Entro en login"
		if(params.password == 'admin'){
			session.administrador = true
			redirect action:'reporte', controller: 'publicacion'
		} else {
			flash.message = 'Usuario o contraseña incorrectos, intente nuevamente'
			redirect uri:'/login'
		}
	}

	def logout(){
		session.administrador = null
		redirect uri:'/login'
		return
	}
}
