package server

import grails.test.mixin.*
import spock.lang.*

import static javax.servlet.http.HttpServletResponse.*

@TestFor(PublicacionController)
@Mock([Publicacion,Usuario,Castrado,Color,CompatibleCon,Edad,Energia,Especie,PapelesAlDia,Proteccion,Raza,Sexo,Tamanio,VacunasAlDia])
class PublicacionControllerSpec extends Specification {
    def setup(){
        new Usuario(facebookId:1156897635,username:'diegomeller',telefono:'112345600',direccion:'Paseo Colon 850',token:'12345',latitud:-58.4621224,longitud:-34.6035515).save()
        new Usuario(username:'email',email:'diego@meller.com',password:'test',telefono:'1145670891',direccion:'Alguna',token:'234567',latitud:-58.4621224,longitud:-34.6035515).save()
        new Castrado(tipo:'Si').save()
        new Castrado(tipo:'No').save()
        new Castrado(tipo:'Desconocido', porDefecto: true).save()

        new Color(nombre:'Apricot').save()
        new Color(nombre:'Atigrado').save()

        new CompatibleCon(compatibleCon: 'Niños').save()
        new CompatibleCon(compatibleCon: 'Otras Mascotas').save()
        new CompatibleCon(compatibleCon: 'No aplica', porDefecto: true).save()

        new Edad(nombre: 'Cachorro (hasta 1 año)').save()
        new Edad(nombre: 'Adulto joven (de 2 a 4 años)').save()
        new Edad(nombre: 'Adulto (de 5 a 9 años)').save()
        new Edad(nombre: 'Viejitos (+ de 10 años)').save()
        new Edad(nombre: 'Desconocido').save()

        new Energia(tipo: 'Muy enérgico').save()
        new Energia(tipo: 'Enérgico').save()
        new Energia(tipo: 'Adicto a la televisión').save()
        new Energia(tipo: 'No aplica', porDefecto: true).save()

        new Especie(tipo:'Perro').save()
        new Especie(tipo:'Gato').save()

        new PapelesAlDia(tipo: 'Si').save()
        new PapelesAlDia(tipo: 'No').save()
        new PapelesAlDia(tipo: 'No Aplica', porDefecto: true).save()

        new Proteccion(tipo:'Guardián').save()
        new Proteccion(tipo:'Algo protector').save()
        new Proteccion(tipo:'Poco o nada protector').save()
        new Proteccion(tipo:'No aplica', porDefecto: true).save()

        new Raza(nombre:'Akita americano',especie: 1).save()
        new Raza(nombre:'Basset Griffon Vendeen',especie:1).save()
        new Raza(nombre:'Basset Hound',especie:1).save()

        new Sexo(tipo: 'Macho').save()
        new Sexo(tipo: 'Hembra').save()
        new Sexo(tipo: 'Desconocido').save()

        new Tamanio(tipo: 'Chico').save()
        new Tamanio(tipo: 'Mediano').save()
        new Tamanio(tipo: 'Grande').save()

        new VacunasAlDia(tipo:'Si').save()
        new VacunasAlDia(tipo:'No').save()
        new VacunasAlDia(tipo:'Desconocido', porDefecto: true).save()
    }

    void "Guardar publicacion Completa OK"() {
        when:
            request.method = 'POST'
            params.usuario=Usuario.get(2)
            params.color=1
            params.castrado=1
            params.compatibleCon=1
            params.edad=1
            params.energia=1
            params.especie=2
            params.papelesAlDia=1
            params.proteccion=2
            params.raza=1
            params.sexo=1
            params.tamanio=1
            params.vacunasAlDia=1
            params.latitud=-58.367289
            params.longitud=-34.6454526
            params.fechaPublicacion= new Date()
            controller.request.addHeader("Accept","application/json")
            controller.save()
            println response.json
        then:
            response.status == SC_CREATED
            Publicacion.list().size() == 1
    }

    void "Crear publicacion sin color"() {
        when:
        request.method = 'POST'
        params.usuario=Usuario.get(2)
        params.castrado=1
        params.compatibleCon=1
        params.edad=1
        params.energia=1
        params.especie=2
        params.papelesAlDia=1
        params.proteccion=2
        params.raza=1
        params.sexo=1
        params.tamanio=1
        params.vacunasAlDia=1
        params.latitud=-58.367289
        params.longitud=-34.6454526
        params.fechaPublicacion= new Date()
        controller.request.addHeader("Accept","application/json")
        controller.save()
        println response.json
        then:
        response.status == 422
        response.json.errors[0].field.equals('color')
    }

    void "Crear publicacion sin edad"() {
        when:
        request.method = 'POST'
        params.usuario=Usuario.get(2)
        params.castrado=1
        params.compatibleCon=1
        params.color=1
        params.energia=1
        params.especie=2
        params.papelesAlDia=1
        params.proteccion=2
        params.raza=1
        params.sexo=1
        params.tamanio=1
        params.vacunasAlDia=1
        params.latitud=-58.367289
        params.longitud=-34.6454526
        params.fechaPublicacion= new Date()
        controller.request.addHeader("Accept","application/json")
        controller.save()
        println response.json
        then:
        response.status == 422
        response.json.errors[0].field.equals('edad')
    }

    void "Crear publicacion sin especie"() {
        when:
        request.method = 'POST'
        params.usuario=Usuario.get(2)
        params.castrado=1
        params.compatibleCon=1
        params.color=1
        params.energia=1
        params.edad=1
        params.papelesAlDia=1
        params.proteccion=2
        params.raza=1
        params.sexo=1
        params.tamanio=1
        params.vacunasAlDia=1
        params.latitud=-58.367289
        params.longitud=-34.6454526
        params.fechaPublicacion= new Date()
        controller.request.addHeader("Accept","application/json")
        controller.save()
        println response.json
        then:
        response.status == 422
        response.json.errors[0].field.equals('especie')
    }

    void "Crear publicacion sin raza"() {
        when:
        request.method = 'POST'
        params.usuario=Usuario.get(2)
        params.castrado=1
        params.compatibleCon=1
        params.color=1
        params.energia=1
        params.edad=1
        params.papelesAlDia=1
        params.proteccion=2
        params.especie=1
        params.sexo=1
        params.tamanio=1
        params.vacunasAlDia=1
        params.latitud=-58.367289
        params.longitud=-34.6454526
        params.fechaPublicacion= new Date()
        controller.request.addHeader("Accept","application/json")
        controller.save()
        println response.json
        then:
        response.status == 422
        response.json.errors[0].field.equals('raza')
    }

    void "Crear publicacion sin sexo"() {
        when:
        request.method = 'POST'
        params.usuario=Usuario.get(2)
        params.castrado=1
        params.compatibleCon=1
        params.color=1
        params.energia=1
        params.edad=1
        params.papelesAlDia=1
        params.proteccion=2
        params.especie=1
        params.raza=1
        params.tamanio=1
        params.vacunasAlDia=1
        params.latitud=-58.367289
        params.longitud=-34.6454526
        params.fechaPublicacion= new Date()
        controller.request.addHeader("Accept","application/json")
        controller.save()
        println response.json
        then:
        response.status == 422
        response.json.errors[0].field.equals('sexo')
    }

    void "Crear publicacion sin tamanio"() {
        when:
        request.method = 'POST'
        params.usuario=Usuario.get(2)
        params.castrado=1
        params.compatibleCon=1
        params.color=1
        params.energia=1
        params.edad=1
        params.papelesAlDia=1
        params.proteccion=2
        params.especie=1
        params.raza=1
        params.sexo=1
        params.vacunasAlDia=1
        params.latitud=-58.367289
        params.longitud=-34.6454526
        params.fechaPublicacion= new Date()
        controller.request.addHeader("Accept","application/json")
        controller.save()
        println response.json
        then:
        response.status == 422
        response.json.errors[0].field.equals('tamanio')
    }


    void "Crear publicacion sin castrado"() {
        when:
        request.method = 'POST'
        params.usuario=Usuario.get(2)
        params.color=1
        params.compatibleCon=1
        params.edad=1
        params.energia=1
        params.especie=2
        params.papelesAlDia=1
        params.proteccion=2
        params.raza=1
        params.sexo=1
        params.tamanio=1
        params.vacunasAlDia=1
        params.latitud=-58.367289
        params.longitud=-34.6454526
        params.fechaPublicacion= new Date()
        controller.request.addHeader("Accept","application/json")
        controller.save()
        println response.json
        def publicacionGuardada = Publicacion.get(1)
        def castradoDefecto = Castrado.findByPorDefecto(true)
        then:
        response.status == SC_CREATED
        Publicacion.list().size() == 1
        publicacionGuardada.castrado == castradoDefecto
        response.json.castrado.id == castradoDefecto.id
    }


    void "Crear publicacion sin compatibleCon"() {
        when:
        request.method = 'POST'
        params.usuario=Usuario.get(2)
        params.color=1
        params.castrado=1
        params.edad=1
        params.energia=1
        params.especie=2
        params.papelesAlDia=1
        params.proteccion=2
        params.raza=1
        params.sexo=1
        params.tamanio=1
        params.vacunasAlDia=1
        params.latitud=-58.367289
        params.longitud=-34.6454526
        params.fechaPublicacion= new Date()
        controller.request.addHeader("Accept","application/json")
        controller.save()
        println response.json
        def publicacionGuardada = Publicacion.get(1)
        def compatibleConDefecto = CompatibleCon.findByPorDefecto(true)
        then:
        response.status == SC_CREATED
        Publicacion.list().size() == 1
        publicacionGuardada.compatibleCon == compatibleConDefecto
        response.json.compatibleCon.id == compatibleConDefecto.id
    }

    void "Crear publicacion sin energia"() {
        when:
        request.method = 'POST'
        params.usuario=Usuario.get(2)
        params.color=1
        params.castrado=1
        params.edad=1
        params.compatibleCon=1
        params.especie=2
        params.papelesAlDia=1
        params.proteccion=2
        params.raza=1
        params.sexo=1
        params.tamanio=1
        params.vacunasAlDia=1
        params.latitud=-58.367289
        params.longitud=-34.6454526
        params.fechaPublicacion= new Date()
        controller.request.addHeader("Accept","application/json")
        controller.save()
        println response.json
        def publicacionGuardada = Publicacion.get(1)
        def energiaDefecto = Energia.findByPorDefecto(true)
        then:
        response.status == SC_CREATED
        Publicacion.list().size() == 1
        publicacionGuardada.energia == energiaDefecto
        response.json.energia.id == energiaDefecto.id
    }

    void "Crear publicacion sin papelesAlDia"() {
        when:
        request.method = 'POST'
        params.usuario=Usuario.get(2)
        params.color=1
        params.castrado=1
        params.edad=1
        params.compatibleCon=1
        params.especie=2
        params.energia=1
        params.proteccion=2
        params.raza=1
        params.sexo=1
        params.tamanio=1
        params.vacunasAlDia=1
        params.latitud=-58.367289
        params.longitud=-34.6454526
        params.fechaPublicacion= new Date()
        controller.request.addHeader("Accept","application/json")
        controller.save()
        println response.json
        def publicacionGuardada = Publicacion.get(1)
        def papelesAlDiaDefecto = PapelesAlDia.findByPorDefecto(true)
        then:
        response.status == SC_CREATED
        Publicacion.list().size() == 1
        publicacionGuardada.papelesAlDia == papelesAlDiaDefecto
        response.json.papelesAlDia.id == papelesAlDiaDefecto.id
    }

    void "Crear publicacion sin proteccion"() {
        when:
        request.method = 'POST'
        params.usuario=Usuario.get(2)
        params.color=1
        params.castrado=1
        params.edad=1
        params.compatibleCon=1
        params.especie=2
        params.energia=1
        params.papelesAlDia=2
        params.raza=1
        params.sexo=1
        params.tamanio=1
        params.vacunasAlDia=1
        params.latitud=-58.367289
        params.longitud=-34.6454526
        params.fechaPublicacion= new Date()
        controller.request.addHeader("Accept","application/json")
        controller.save()
        println response.json
        def publicacionGuardada = Publicacion.get(1)
        def proteccionDefecto = Proteccion.findByPorDefecto(true)
        then:
        response.status == SC_CREATED
        Publicacion.list().size() == 1
        publicacionGuardada.proteccion == proteccionDefecto
        response.json.proteccion.id == proteccionDefecto.id
    }

    void "Crear publicacion sin vacunasAlDia"() {
        when:
        request.method = 'POST'
        params.usuario=Usuario.get(2)
        params.color=1
        params.castrado=1
        params.edad=1
        params.compatibleCon=1
        params.especie=2
        params.energia=1
        params.papelesAlDia=2
        params.raza=1
        params.sexo=1
        params.tamanio=1
        params.proteccion=1
        params.latitud=-58.367289
        params.longitud=-34.6454526
        params.fechaPublicacion= new Date()
        controller.request.addHeader("Accept","application/json")
        controller.save()
        println response.json
        def publicacionGuardada = Publicacion.get(1)
        def vacunasAlDiaDefecto = VacunasAlDia.findByPorDefecto(true)
        then:
        response.status == SC_CREATED
        Publicacion.list().size() == 1
        publicacionGuardada.vacunasAlDia == vacunasAlDiaDefecto
        response.json.vacunasAlDia.id == vacunasAlDiaDefecto.id
    }

    void "Crear publicacion sin tipoPublicacion"() {
        when:
        request.method = 'POST'
        params.usuario=Usuario.get(2)
        params.color=1
        params.castrado=1
        params.edad=1
        params.compatibleCon=1
        params.especie=2
        params.energia=1
        params.papelesAlDia=2
        params.raza=1
        params.sexo=1
        params.tamanio=1
        params.vacunasAlDia=1
        params.proteccion=1
        params.latitud=-58.367289
        params.longitud=-34.6454526
        params.fechaPublicacion= new Date()
        controller.request.addHeader("Accept","application/json")
        controller.save()
        println response.json
        def publicacionGuardada = Publicacion.get(1)
        then:
        response.status == SC_CREATED
        Publicacion.list().size() == 1
        publicacionGuardada.tipoPublicacion == 1
        response.json.tipoPublicacion == 1
    }

    void "Crear publicacion entonces queda activa"() {
        when:
        request.method = 'POST'
        params.usuario=Usuario.get(2)
        params.color=1
        params.castrado=1
        params.edad=1
        params.compatibleCon=1
        params.especie=2
        params.energia=1
        params.papelesAlDia=2
        params.raza=1
        params.sexo=1
        params.tamanio=1
        params.vacunasAlDia=1
        params.proteccion=1
        params.latitud=-58.367289
        params.longitud=-34.6454526
        params.fechaPublicacion= new Date()
        controller.request.addHeader("Accept","application/json")
        controller.save()
        println response.json
        def publicacionGuardada = Publicacion.get(1)
        then:
        response.status == SC_CREATED
        Publicacion.list().size() == 1
        publicacionGuardada.activa
        response.json.activa
    }

    void "Crear publicacion toma Latitud y Longitud de Usuario"() {
        when:
        request.method = 'POST'
        def usuario = Usuario.get(2)
        params.usuario= usuario
        params.color=1
        params.castrado=1
        params.edad=1
        params.compatibleCon=1
        params.especie=2
        params.energia=1
        params.papelesAlDia=2
        params.raza=1
        params.sexo=1
        params.tamanio=1
        params.vacunasAlDia=1
        params.proteccion=1
        params.fechaPublicacion= new Date()
        controller.request.addHeader("Accept","application/json")
        controller.save()
        println response.json
        def publicacionGuardada = Publicacion.get(1)
        then:
        response.status == SC_CREATED
        Publicacion.list().size() == 1
        publicacionGuardada.latitud == usuario.latitud
        publicacionGuardada.longitud == usuario.longitud
        response.json.latitud == usuario.latitud
        response.json.longitud == usuario.longitud
    }

    void "Crear publicacion toma Latitud y Longitud de Parametros"() {
        when:
        request.method = 'POST'
        def usuario = Usuario.get(2)
        params.usuario= usuario
        params.color=1
        params.castrado=1
        params.edad=1
        params.compatibleCon=1
        params.especie=2
        params.energia=1
        params.papelesAlDia=2
        params.raza=1
        params.sexo=1
        params.tamanio=1
        params.vacunasAlDia=1
        params.proteccion=1
        params.fechaPublicacion= new Date()
        params.latitud = 0
        params.longitud = 1
        controller.request.addHeader("Accept","application/json")
        controller.save()
        println response.json
        def publicacionGuardada = Publicacion.get(1)
        then:
        response.status == SC_CREATED
        Publicacion.list().size() == 1
        publicacionGuardada.latitud == 0
        publicacionGuardada.longitud == 1
        response.json.latitud == 0
        response.json.longitud == 1
    }
}
