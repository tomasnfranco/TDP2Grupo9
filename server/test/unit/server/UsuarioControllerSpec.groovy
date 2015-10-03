package server

import grails.test.mixin.*
import spock.lang.*
import static org.springframework.http.HttpStatus.*
import static javax.servlet.http.HttpServletResponse.*

@TestFor(UsuarioController)
@Mock(Usuario)
class UsuarioControllerSpec extends Specification {

    void "Login GET"() {
        when: 'el login es metodo get'
        request.method = 'GET'
        controller.login()
        then: 'devuelve error 405 Method Not Allowed'
        response.status == SC_METHOD_NOT_ALLOWED
    }
    void "Login sin facebookId devuelve NOT_FOUND"() {
        when: 'no se manda fb id'
        request.method = 'POST'
        controller.login()
        then: 'devuelve not found'
        response.status == SC_NOT_FOUND
    }
    void "Login con facebookId NO registrado devuelve NOT_FOUND"() {
        when: 'el fb id no existe'
            request.method = 'POST'
            params.facebookId = 12345
            controller.login()
        then: 'devuelve not found'
            response.status == SC_NOT_FOUND
    }

    void "Login con usuario OK"(){
        setup:
            new Usuario(facebookId: 12345,telefono:'44444444',direccion:'abcdefg',activo: true).save()
            request.method = 'POST'
            params.facebookId = 12345
        when:
            controller.login()
        then:
            response.status == SC_OK
    }

    void "Login con usuario Inactivo"(){
        when:
        request.method = 'POST'
        new Usuario(facebookId: 12345,telefono:'44444444',direccion:'abcdefg',activo: false).save()
        params.facebookId = 12345
        controller.login()
        then:
        response.status == SC_UNAUTHORIZED
    }

    void "Login devuelve token"(){
        setup:
        def usuario1 = new Usuario(facebookId: 12345,telefono:'44444444',direccion:'abcdefg',activo: true).save()
        mockDomain(Usuario, [usuario1])
        request.method = 'POST'
        params.facebookId = 12345
        when:
        request.contentType = 'application/json'
        controller.request.addHeader("Accept","application/json")
        controller.login()
        then:
            response.json.token != ""
    }
}
