import server.Usuario
import server.Castrado
class BootStrap {

    def init = { servletContext ->
		new Usuario(facebookId:1156897635,username:'diegomeller',telefono:'112345600',direccion:'Paseo Colon 850',token:'12345').save()
		new Usuario(username:'email',email:'diego@meller.com',password:'test',telefono:'1145670891',direccion:'Alguna',token:'234567').save()
        new Castrado(tipo:'Si').save()
        new Castrado(tipo:'No').save()
        new Castrado(tipo:'Desconocido').save()
    }
    def destroy = {
    }
}
