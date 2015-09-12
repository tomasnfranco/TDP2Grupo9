class ApiFilters {
   def filters = {
        sampleFilter(uri:'/api/**') {
			before = {
				println "Entro en: $controllerName accion $actionName"
			}
		}
   }
}