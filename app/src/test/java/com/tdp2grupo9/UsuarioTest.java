package com.tdp2grupo9;

import com.tdp2grupo9.modelo.Publicacion;
import com.tdp2grupo9.modelo.Usuario;
import com.tdp2grupo9.modelo.publicacion.Castrado;
import com.tdp2grupo9.modelo.publicacion.Color;
import com.tdp2grupo9.modelo.publicacion.CompatibleCon;
import com.tdp2grupo9.modelo.publicacion.Edad;
import com.tdp2grupo9.modelo.publicacion.Energia;
import com.tdp2grupo9.modelo.publicacion.Especie;
import com.tdp2grupo9.modelo.publicacion.PapelesAlDia;
import com.tdp2grupo9.modelo.publicacion.Proteccion;
import com.tdp2grupo9.modelo.publicacion.Raza;
import com.tdp2grupo9.modelo.publicacion.Sexo;
import com.tdp2grupo9.modelo.publicacion.Tamanio;
import com.tdp2grupo9.modelo.publicacion.VacunasAlDia;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class UsuarioTest {

    private Usuario usuario;

    private Integer crearPublicacion(String token) {
        Publicacion publicacion = new Publicacion();
        publicacion.setNombreMascota("PRUEBAUSUARIO");
        publicacion.setEspecie(new Especie(1));
        publicacion.setRaza(new Raza(1));
        publicacion.setColor(new Color(1));
        publicacion.setSexo(new Sexo(1));
        publicacion.setTamanio(new Tamanio(1));
        publicacion.setEdad(new Edad(2));
        publicacion.setCompatibleCon(new CompatibleCon(1));
        publicacion.setVacunasAlDia(new VacunasAlDia(1));
        publicacion.setPapelesAlDia(new PapelesAlDia(1));
        publicacion.setCastrado(new Castrado(1));
        publicacion.setProteccion(new Proteccion(1));
        publicacion.setEnergia(new Energia(1));
        publicacion.setVideoLink("");
        publicacion.setLatitud(10.0);
        publicacion.setLongitud(15.0);
        publicacion.setNecesitaTransito(false);
        publicacion.setRequiereCuidadosEspeciales(false);
        publicacion.setContacto("ROMI");
        publicacion.setCondiciones("");

        publicacion.guardarPublicacion(token);
        return publicacion.getId();
    }

    @Before
    public void inicializar() {
        Usuario.getInstancia().resetearAtributos();
        Usuario.getInstancia().setFacebookId(1156897635L);
        Usuario.getInstancia().setToken("12345");
        Usuario.getInstancia().setId(1);
        usuario = Usuario.getInstancia();
    }

    @Test
    public void postularmeEnPublicacionDeOtroUsuario() {
        Integer idPublicacion = crearPublicacion("234567");
        usuario.quieroAdoptar(idPublicacion);
        Publicacion publicacion = Publicacion.obtenerPublicacion(usuario.getToken(), idPublicacion);
        assertTrue("No se encuentra el usuario entre los postulantes", Collections.frequency(publicacion.getPostulantesIds(), usuario.getId()) == 1);
    }

    @Test
    public void postularmeDosVecesEnUnaMismaPublicacionNoEsPosible() {
        Integer idPublicacion = crearPublicacion("234567");
        usuario.quieroAdoptar(idPublicacion);
        usuario.quieroAdoptar(idPublicacion);
        Publicacion publicacion = Publicacion.obtenerPublicacion(usuario.getToken(), idPublicacion);
        assertTrue("El postulante tiene que estar una vez", Collections.frequency(publicacion.getPostulantesIds(), usuario.getId()) == 1);
    }

    @Test
    public void postularmeEnMiPublicacionNoEsPosible() {
        Integer idPublicacion = crearPublicacion(usuario.getToken());
        usuario.quieroAdoptar(idPublicacion);
        Publicacion publicacion = Publicacion.obtenerPublicacion(usuario.getToken(), idPublicacion);
        assertTrue("El postulante tiene que estar una vez", Collections.frequency(publicacion.getPostulantesIds(), usuario.getId()) == 0);
    }


    @Test
    public void misPublicaciones() {
        Integer publicacionId1 = crearPublicacion(usuario.getToken());
        Integer publicacionId2 = crearPublicacion(usuario.getToken());
        Integer publicacionId3 = crearPublicacion(usuario.getToken());
        List<Publicacion> publicaciones = usuario.obtenerMisPublicaciones(0, 0);
        List<Integer> publicacionesIds = new ArrayList<>();
        for (Publicacion p : publicaciones) {
            publicacionesIds.add(p.getId());
        }
        assertTrue("La publicacion no se encuentra", Collections.frequency(publicacionesIds, publicacionId1) == 1);
        assertTrue("La publicacion no se encuentra", Collections.frequency(publicacionesIds, publicacionId2) == 1);
        assertTrue("La publicacion no se encuentra", Collections.frequency(publicacionesIds, publicacionId3) == 1);
    }

    @Test
    public void misPostulaciones() {
        Integer publicacionId1 = crearPublicacion("234567");
        Integer publicacionId2 = crearPublicacion("234567");
        Integer publicacionId3 = crearPublicacion("234567");
        usuario.quieroAdoptar(publicacionId1);
        usuario.quieroAdoptar(publicacionId2);
        usuario.quieroAdoptar(publicacionId3);
        List<Publicacion> postulaciones = usuario.obtenerMisPostulaciones(0, 0);
        List<Integer> postulacionesIds = new ArrayList<>();
        for (Publicacion p : postulaciones) {
            postulacionesIds.add(p.getId());
        }
        assertTrue("La postulacion no se encuentra", Collections.frequency(postulacionesIds, publicacionId1) == 1);
        assertTrue("La postulacion no se encuentra", Collections.frequency(postulacionesIds, publicacionId2) == 1);
        assertTrue("La postulacion no se encuentra", Collections.frequency(postulacionesIds, publicacionId3) == 1);
    }

    @Test
    public void concretarAdopcion() {
        Integer postulanteId = 2;
        String postulanteToken = "234567";

        Integer publicacionId = crearPublicacion(usuario.getToken());
        Publicacion.quieroAdoptar(postulanteToken, publicacionId);
        usuario.concretarAdopcion(publicacionId, postulanteId);

        Publicacion publicacion = Publicacion.obtenerPublicacion(usuario.getToken(), publicacionId);
        assertTrue("La publicacion no esta concreatada", publicacion.getConcreatada());
        assertTrue("El postulante no es el elegido", publicacion.getPostulanteConcretadoId().equals(postulanteId));
    }

}
