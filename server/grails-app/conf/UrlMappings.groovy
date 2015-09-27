class UrlMappings {

	static mappings = {
		"/api/$controller/$id"(parseRequest:true){
			format = "json"
			action = [GET:"show", POST:"save", PUT:"update", DELETE:"delete"]
			constraints {
				// apply constraints here
			}
		}
		"/api/publicacion/atributos"{
			format = 'json'
			action = 'atributos'
			controller = 'publicacion'
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
        "500"(view:'/error')
	}
}
