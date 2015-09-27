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


    void "Unico facebookId para registrar"() {
        when:
            new Usuario(facebookId: 12345).save()
            def user2 = new Usuario(facebookId: 12345)
            user2.validate()
        then:
            user2.hasErrors()
    }

}
