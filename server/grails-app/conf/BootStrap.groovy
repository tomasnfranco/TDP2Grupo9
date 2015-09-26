import server.Color
import server.Usuario
import server.Castrado
import server.CompatibleCon
import server.Edad
import server.Energia
import server.Especie
import server.PapelesAlDia
import server.Proteccion
import server.Sexo
import server.Tamanio
import server.VacunasAlDia
import server.Publicacion

class BootStrap {

    def init = { servletContext ->
		new Usuario(facebookId:1156897635,username:'diegomeller',telefono:'112345600',direccion:'Paseo Colon 850',token:'12345',latitud:-58.4621224,longitud:-34.6035515).save()
		new Usuario(username:'email',email:'diego@meller.com',password:'test',telefono:'1145670891',direccion:'Alguna',token:'234567',latitud:-58.4621224,longitud:-34.6035515).save()
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

        new CompatibleCon(compatibleCon: 'Niños').save()
        new CompatibleCon(compatibleCon: 'Otras Mascotas').save()

        new Edad(nombre: 'Cachorro (hasta 1 año)').save()
        new Edad(nombre: 'Adulto joven (de 2 a 4 años)').save()
        new Edad(nombre: 'Adulto (de 5 a 9 años)').save()
        new Edad(nombre: 'Viejitos (+ de 10 años)').save()
        new Edad(nombre: 'Desconocido').save()

        new Energia(tipo: 'Muy enérgico').save()
        new Energia(tipo: 'Enérgico').save()
        new Energia(tipo: 'Adicto a la televisión').save()
        new Energia(tipo: 'No aplica').save()

        new Especie(tipo:'Perro').save()
        new Especie(tipo:'Gato').save()

        new PapelesAlDia(tipo: 'Si').save()
        new PapelesAlDia(tipo: 'No').save()
        new PapelesAlDia(tipo: 'No Aplica').save()

        new Proteccion(tipo:'Guardián').save()
        new Proteccion(tipo:'Algo protector').save()
        new Proteccion(tipo:'Poco o nada protector').save()
        new Proteccion(tipo:'No aplica').save()

        new Sexo(tipo: 'Macho').save()
        new Sexo(tipo: 'Hembra').save()
        new Sexo(tipo: 'Desconocido').save()

        new Tamanio(tipo: 'Chico').save()
        new Tamanio(tipo: 'Mediano').save()
        new Tamanio(tipo: 'Grande').save()

        new VacunasAlDia(tipo:'Si').save()
        new VacunasAlDia(tipo:'No').save()
        new VacunasAlDia(tipo:'Desconocido').save()
				
		new Publicacion(nombreMascota:'bobby',publicador:2,color:1,castrado:1,compatibleCon:1,edad:1,energia:1,especie:2,papelesAlDia:1,proteccion:2,sexo:1,tamanio:1,vacunasAlDia:1,latitud:-58.367289,longitud:-34.6454526).save()
		new Publicacion(nombreMascota:'otto',publicador:2,color:1,castrado:1,compatibleCon:1,edad:1,energia:1,especie:2,papelesAlDia:1,proteccion:2,sexo:1,tamanio:1,vacunasAlDia:1,latitud:-58.4226265,longitud:-34.6098885).save()
		new Publicacion(nombreMascota:'panchita tiene pelos largos blancos y sucios por estar en el patio',publicador:2,color:1,castrado:1,compatibleCon:1,edad:1,energia:1,especie:2,papelesAlDia:1,proteccion:2,sexo:1,tamanio:1,vacunasAlDia:1,latitud:-58.5242344,longitud:-34.6192385).save()
		

    }
    def destroy = {
    }
}

