import static org.springframework.http.HttpStatus.UNAUTHORIZED
import server.Usuario

class ApiFilters {
   def filters = {
        todosApi(uri:'/api/**') {
			before = {
				if(controllerName != 'usuario' || (actionName != 'save' && actionName != 'login')) {
					//El usuario debe estar logueado, debe tener un token valido
					//println "Entro en: $controllerName accion $actionName"
					println "Parametros normales: $params"
					println "parametros json: ${request.getJSON()}"
					if(params.token == null){
						params.token = request.getJSON()["token"]
					}
					if(params.token == null || params.token == ''){
						render status: UNAUTHORIZED
						return false
					}

					def userLogueado = Usuario.findByToken(params.token)
					if(userLogueado == null){
						render status: UNAUTHORIZED
						return false
					}
					params.usuario = userLogueado;
					params.publicador = userLogueado.id;
				}
				println "$controllerName : $actionName"
			}
		}

	   administrador(controller:"usuario|publicacion",action:'administrar|publicaciones|ver|reporte'){
		   before={
			   if(!session.administrador){
				   flash.message = "Debe iniciar sesión para entrar"
				   redirect uri:'/login'
				   return false
			   }
		   }
	   }
   }
}