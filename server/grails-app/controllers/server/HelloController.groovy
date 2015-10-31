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
}
