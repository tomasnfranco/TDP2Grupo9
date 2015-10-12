package com.tdp2grupo9;

import com.tdp2grupo9.modelo.Publicacion;
import com.tdp2grupo9.modelo.Usuario;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class UsuarioTest {

    private Usuario usuario;

    @Before
    public void inicializar() {
        Usuario.getInstancia().resetearAtributos();
        Usuario.getInstancia().setFacebookId(1156897635L);
        Usuario.getInstancia().setToken("12345");
        Usuario.getInstancia().setId(1);
        usuario = Usuario.getInstancia();
    }

    @Test
    public void postularmeEnPublicacion() {
        Integer idPublicacion = 4;
        usuario.quieroAdoptar(idPublicacion);
        Publicacion publicacion = Publicacion.obtenerPublicacion(usuario.getToken(), idPublicacion);
        assertTrue("El postulante tiene que estar una vez", publicacion.getPostulantes().size() == 1);
        for (Integer idPostulante : publicacion.getPostulantes()) {
            assertEquals("El id del postulante no coincide con el id del usuario", usuario.getId(), idPostulante);
        }
    }

    @Test
    public void postularmeDosVecesEnUnaMismaPublicacion() {
        Integer idPublicacion = 2;
        usuario.quieroAdoptar(idPublicacion);
        usuario.quieroAdoptar(idPublicacion);
        Publicacion publicacion = Publicacion.obtenerPublicacion(usuario.getToken(), idPublicacion);
        assertTrue("El postulante tiene que estar una vez", publicacion.getPostulantes().size() == 1);
    }

    @Test
    public void misPublicaciones() {
        List<Publicacion> publicaciones = usuario.obtenerMisPublicaciones(0, 0);
        assertTrue(publicaciones.size() > 0);
    }

    @Test
    public void misPostulaciones() {
        List<Publicacion> publicaciones = usuario.obtenerMisPostulaciones(0, 0);
        assertTrue(publicaciones.size() > 0);
    }

    @Test
    public void concretarAdopcion() {
        Integer idPublicacion = 2;
        Integer idPostulante = 2;
        usuario.concretarAdopcion(idPublicacion, idPostulante);

        Publicacion publicacion = Publicacion.obtenerPublicacion(usuario.getToken(), idPublicacion);
        //TODO PROBAR QUE ESTA CERRADA
    }

}
