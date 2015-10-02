package server
import server.Usuario
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Usuario)
class UsuarioSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "No permite Grabar usuario sin telefono"(){
        when:
        def user = new Usuario(facebookId: 12345,direccion:'abc')
        user.validate()
        then:
        user.hasErrors()
    }

    void "No permite Grabar usuario sin direccion"(){
        when:
        def user = new Usuario(facebookId: 12345,telefono:'44444')
        user.validate()
        then:
        user.hasErrors()
    }

    void "Usuario con fbid, telefono y direccion es correcto"(){
        when:
        def user = new Usuario(facebookId: 12345,telefono:'44444',direccion:'abcdefg')
        then:
        user.validate()
        !(user.hasErrors())
    }

    void "Guarda Usuario y lo levanta por facebookId"(){
        when:
        def user = new Usuario(facebookId: 12345,telefono:'44444',direccion:'abcdefg')
        user.save()
        def userBuscado = Usuario.findByFacebookId(12345)
        then:
        userBuscado != null
        userBuscado.id == user.id
    }

    void "Unico facebookId para registrar"() {
        when:
            new Usuario(facebookId: 12345,direccion: 'abc',telefono: '12345').save()
            def user2 = new Usuario(facebookId: 12345,direccion:'bcd',telefono:'234567')
            user2.validate()
        then:
            user2.hasErrors()
            user2.errors['facebookId'] != null
    }
}
