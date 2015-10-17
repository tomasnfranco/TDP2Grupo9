package com.tdp2grupo9;

import com.tdp2grupo9.modelo.Alerta;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class AlertaTest {

    private Usuario usuario;

    private Integer crearAlerta(String token) {
        Alerta alerta = new Alerta();
        //alerta.setTipoPublicacion(1);
        alerta.setEspecie(new Especie(1));
        alerta.setRaza(new Raza(1));
        alerta.setColor(new Color(1));
        alerta.setSexo(new Sexo(1));
        alerta.setTamanio(new Tamanio(1));
        alerta.setEdad(new Edad(2));
        alerta.setCompatibleCon(new CompatibleCon(1));
        alerta.setVacunasAlDia(new VacunasAlDia(1));
        alerta.setPapelesAlDia(new PapelesAlDia(1));
        alerta.setCastrado(new Castrado(1));
        alerta.setProteccion(new Proteccion(1));
        alerta.setEnergia(new Energia(1));
        alerta.setLatitud(10.0);
        alerta.setLongitud(15.0);
        //alerta.setNecesitaTransito(false);
        //alerta.setRequiereCuidadosEspeciales(false);

        alerta.guardarAlerta(token);
        return alerta.getId();
    }

    @Before
    public void inicializar() {
        Usuario.getInstancia().resetearAtributos();
        Usuario.getInstancia().setFacebookId(1156897635L);
        Usuario.getInstancia().setToken("12345");
        usuario = Usuario.getInstancia();
    }

    @Test
    public void guardarAlerta() {
        Integer alertaId = crearAlerta(usuario.getToken());
        assertTrue("El id de la alerta debe ser mayor a 0", alertaId > 0);
    }

    @Test
    public void obtenerAlerta() {
        Integer alertaId = crearAlerta(usuario.getToken());
        Alerta alerta = Alerta.obtenerAlerta(usuario.getToken(), alertaId);
        assertEquals("El id de la alerta debe concidir con el buscado", alertaId, alerta.getId());
    }

    @Test
    public void borrarAlerta() {
        Integer alertaId = crearAlerta(usuario.getToken());
        Alerta alerta = Alerta.obtenerAlerta(usuario.getToken(), alertaId);
        alerta.borrarAlerta(usuario.getToken());
        alerta = Alerta.obtenerAlerta(usuario.getToken(), alertaId);
        assertTrue("El id de la alerta debe ser cero, esta borrada", alerta.getId() == 0);
    }

    @Test
    public void misAlertas() {
        Integer alertaId1 = crearAlerta(usuario.getToken());
        Integer alertaId2 = crearAlerta(usuario.getToken());
        Integer alertaId3 = crearAlerta(usuario.getToken());

        List<Alerta> alertas = usuario.obtenerMisAlertas(0, 0);
        List<Integer> alertasIds = new ArrayList<>();
        for (Alerta a : alertas) {
            alertasIds.add(a.getId());
        }

        assertTrue("La alerta no se encuentra", Collections.frequency(alertasIds, alertaId1) == 1);
        assertTrue("La alerta no se encuentra", Collections.frequency(alertasIds, alertaId2) == 1);
        assertTrue("La alerta no se encuentra", Collections.frequency(alertasIds, alertaId3) == 1);
    }
}
