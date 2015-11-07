class UrlMappings {

	static mappings = {
		"/api/usuario/login"{
			controller='usuario'
			action = 'login'
			format='json'
		}
		"/api/usuario/logout" {
			controller='usuario'
			action= 'logout'
			format='json'
		}
		"/api/publicacion/atributos" {
			controller='publicacion'
			action= 'atributos'
			format='json'
		}
		"/api/publicacion/buscar" {
			controller='publicacion'
			action= 'buscar'
			format='json'
		}
		"/api/$controller/$id"(parseRequest:true){
			format = "json"
			action = [GET:"show", POST:"save", PUT:"update", DELETE:"delete"]
			constraints {
				// apply constraints here
			}
		}

		"/api/$controller"{
			format = "json"
			action = [GET:"index", POST:"save"]
		}

		"/api/$controller/$action"{
			format = "json"
		}
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }


        "/"(view:"/index")
		"/login"(view:"/login")
        "500"(view:'/error')
	}
}

