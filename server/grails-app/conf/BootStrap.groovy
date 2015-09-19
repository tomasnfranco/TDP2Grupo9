import server.Color
import server.Usuario
import server.Castrado

class BootStrap {

    def init = { servletContext ->
		new Usuario(facebookId:1156897635,username:'diegomeller',telefono:'112345600',direccion:'Paseo Colon 850',token:'12345').save()
		new Usuario(username:'email',email:'diego@meller.com',password:'test',telefono:'1145670891',direccion:'Alguna',token:'234567').save()
        new Castrado(tipo:'Si').save()
        new Castrado(tipo:'No').save()
        new Castrado(tipo:'Desconocido').save()
        new Color(nombre:'Apricot').save()
        new Color(nombre:'Atigrado').save()
        new Color(nombre:'Beige').save()
        new Color(nombre:'Blanco').save()
        new Color(nombre:'Canela').save()
        new Color(nombre:'Fuego').save()
        new Color(nombre:'Gris').save()
        new Color(nombre:'Marrón').save()
        new Color(nombre:'Naranja').save()
        new Color(nombre:'Negro').save()
        new Color(nombre:'Sal y pimienta').save()
        new Color(nombre:'Te').save()
        new Color(nombre:'Tricolor').save()
        new Color(nombre:'Otro').save()
    }
    def destroy = {
    }
}
