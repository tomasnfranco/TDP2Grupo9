import server.Usuario
class BootStrap {

    def init = { servletContext ->
		new Usuario(facebookId:1156897635,username:'diegomeller',telefono:'112345600',direccion:'Paseo Colon 850').save()
		new Usuario(username:'email',email:'diego@meller.com',password:'test',telefono:'1145670891',direccion:'Alguna').save()
    }
    def destroy = {
    }
}
