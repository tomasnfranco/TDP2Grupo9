package server

import grails.test.mixin.*
import spock.lang.*

@TestFor(UsuarioController)
@Mock(Usuario)
class UsuarioControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }
}
