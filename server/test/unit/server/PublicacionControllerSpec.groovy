package server



import grails.test.mixin.*
import spock.lang.*

@TestFor(PublicacionController)
@Mock(Publicacion)
class PublicacionControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }
}
