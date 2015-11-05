package com.tdp2grupo9;

import com.tdp2grupo9.modelo.Mensaje;
import com.tdp2grupo9.modelo.Usuario;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class MensajeTest {

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
    public void preguntaRespuestaObtener() {
        Mensaje mp = new Mensaje();
        mp.setPublicacionId(2);
        mp.setPregunta("Es jugueton??");
        mp.setUsuarioRespuestaId(3);
        mp.guardarPregunta(usuario.getToken());
        assertTrue(mp.getId() > 0);

        Mensaje mr = new Mensaje();
        mr.setId(mp.getId());
        mr.setRespuesta("Sii, mucho. Es muy alegre.");
        mr.responderPregunta("234567");

        Mensaje m = Mensaje.obtenerMensaje(usuario.getToken(), mp.getId());

        assertEquals("El id no coincide", mp.getId(), m.getId());
        assertEquals("La pregunta no coincide", mp.getPregunta(), m.getPregunta());
        assertEquals("La respuesta no coincide", mr.getRespuesta(), m.getRespuesta());
    }

    @Test
    public void usuarioPreguntaEnPublicacion() {
        //EL MENSAJE ES PUBLICO
        Mensaje mp = new Mensaje();
        mp.setPublicacionId(2);
        mp.setPregunta("Es jugueton??");
        mp.guardarPregunta(usuario.getToken());
        assertTrue(mp.getId() > 0);

    }

    @Test
    public void publicadorPreguntaEnPublicacionAPostulante() {
        //EL MENSAJE ES PRIVADO
        Mensaje mp = new Mensaje();
        mp.setPublicacionId(7);
        mp.setPregunta("Tenes jardin?");
        mp.setUsuarioPreguntaId(usuario.getId());
        mp.setUsuarioRespuestaId(3);
        mp.guardarPregunta(usuario.getToken());
        assertTrue(mp.getId() > 0);

    }



}
