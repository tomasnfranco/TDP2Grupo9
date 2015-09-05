class UrlMappings {

	static mappings = {
		"/api/$controller/$id"{
			format = "json"
			action = [GET:"show", POST:"save", PUT:"update", DELETE:"remove"]
			constraints {
				// apply constraints here
			}
		}
		"/api/$controller"{
			format = "json"
			action = [GET:"list", POST:"save"]
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
