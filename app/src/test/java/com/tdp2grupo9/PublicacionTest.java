package com.tdp2grupo9;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import com.tdp2grupo9.modelo.Publicacion;
import com.tdp2grupo9.modelo.TipoPublicacion;
import com.tdp2grupo9.modelo.Usuario;
import com.tdp2grupo9.modelo.publicacion.Castrado;
import com.tdp2grupo9.modelo.publicacion.Color;
import com.tdp2grupo9.modelo.publicacion.CompatibleCon;
import com.tdp2grupo9.modelo.publicacion.Edad;
import com.tdp2grupo9.modelo.publicacion.Energia;
import com.tdp2grupo9.modelo.publicacion.Especie;
import com.tdp2grupo9.modelo.Imagen;
import com.tdp2grupo9.modelo.publicacion.PapelesAlDia;
import com.tdp2grupo9.modelo.publicacion.Proteccion;
import com.tdp2grupo9.modelo.publicacion.Raza;
import com.tdp2grupo9.modelo.publicacion.Sexo;
import com.tdp2grupo9.modelo.publicacion.Tamanio;
import com.tdp2grupo9.modelo.publicacion.VacunasAlDia;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class PublicacionTest {
	
	Usuario usuario;

    private Integer crearPublicacion(String token) {
        Publicacion publicacion = new Publicacion();
        publicacion.setTipoPublicacion(TipoPublicacion.ADOPCION);
        publicacion.setNombreMascota("PRUEBAPUBLICACION");
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
        publicacion.setLongitud(10.0);
        publicacion.setLatitud(15.0);
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
		usuario = Usuario.getInstancia();
	}

    @Test
    public void guardarPublicacionBienArmadaConImagenes() {

        Imagen imagen1 = new Imagen();
        imagen1.setImg(Imagen.bytesFromBase64DEFAULT(ImagenTest.IMAGEN_GRANDE1));
        Imagen imagen2 = new Imagen();
        imagen2.setImg(Imagen.bytesFromBase64DEFAULT(ImagenTest.IMAGEN_GRANDE2));
        Imagen imagen3 = new Imagen();
        imagen3.setImg(Imagen.bytesFromBase64DEFAULT(ImagenTest.IMAGEN_GRANDE2));

        Publicacion publicacion = new Publicacion();
        publicacion.setNombreMascota("TRESIMAGENES");
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
        publicacion.setLatitud(-34.59767);
        publicacion.setLongitud(-58.4430195);
        publicacion.setNecesitaTransito(false);
        publicacion.setRequiereCuidadosEspeciales(false);
        publicacion.setContacto("ROMI");
        publicacion.setCondiciones("");
        publicacion.addImagen(imagen1);
        publicacion.addImagen(imagen2);
        publicacion.addImagen(imagen3);

        int id = publicacion.getId();
        publicacion.guardarPublicacion(usuario.getToken());
        assertTrue("No cambio el id de la publicacion guardada", id != publicacion.getId());

        for (Imagen img : publicacion.getImagenes()) {
            assertTrue("El id de la imagen obtenida no es mayor a cero", img.getId() > 0);
        }

    }

	@Test
	public void guardarPublicacionBienArmadaConTresImagenes() {
        String base64 = "R0lGODlhDwAPAKECAAAAzMzM/////wAAACwAAAAADwAPAAACIISPeQHsrZ5ModrLlN48CXF8m2iQ3YmmKqVlRtW4MLwWACH+H09wdGltaXplZCBieSBVbGVhZCBTbWFydFNhdmVyIQAAOw==";

		Imagen imagen1 = new Imagen();
		imagen1.setImg(Imagen.bytesFromBase64DEFAULT(base64));
        Imagen imagen2 = new Imagen();
        imagen2.setImg(Imagen.bytesFromBase64DEFAULT(base64));
        Imagen imagen3 = new Imagen();
        imagen3.setImg(Imagen.bytesFromBase64DEFAULT(base64));

		Publicacion publicacion = new Publicacion();
        publicacion.setTipoPublicacion(TipoPublicacion.ADOPCION);
		publicacion.setNombreMascota("TRESIMAGENES");
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
		publicacion.setLatitud(-34.59767);
		publicacion.setLongitud(-58.4430195);
		publicacion.setNecesitaTransito(false);
		publicacion.setRequiereCuidadosEspeciales(false);
		publicacion.setContacto("ROMI");
		publicacion.setCondiciones("");
		publicacion.addImagen(imagen1);
        publicacion.addImagen(imagen2);
        publicacion.addImagen(imagen3);
		
		int id = publicacion.getId();
		publicacion.guardarPublicacion(usuario.getToken());
		assertTrue("No cambio el id de la publicacion guardada", id != publicacion.getId());

        for (Imagen img : publicacion.getImagenes()) {
            assertTrue("El id de la imagen obtenida no es mayor a cero", img.getId() > 0);
        }

	}

    @Test
    public void guardarPublicacionProbarLatitudLongitud() {

        Publicacion publicacion = new Publicacion();
        publicacion.setTipoPublicacion(TipoPublicacion.ADOPCION);
        publicacion.setNombreMascota("LATLONG");
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
        publicacion.setLatitud(15.0);
        publicacion.setLongitud(10.0);
        publicacion.setNecesitaTransito(false);
        publicacion.setRequiereCuidadosEspeciales(false);
        publicacion.setContacto("ROMI");
        publicacion.setCondiciones("");

        int id = publicacion.getId();
        publicacion.guardarPublicacion(usuario.getToken());
        assertTrue("No cambio el id de la publicacion guardada", id != publicacion.getId());
        assertTrue("La latitud no coincide", 15.0 == publicacion.getLatitud().doubleValue());
        assertTrue("La longitud no coincide", 10.0 == publicacion.getLongitud().doubleValue());

    }
	
    @Test
	public void buscarPublicacionesPropiasNoDevuelveNada() {

        Publicacion publicacionFiltros = new Publicacion();
        publicacionFiltros.setEspecie(new Especie(1));
        publicacionFiltros.setRaza(new Raza(1));
        publicacionFiltros.setColor(new Color(1));
        publicacionFiltros.setSexo(new Sexo(1));
        publicacionFiltros.setTamanio(new Tamanio(1));
        publicacionFiltros.setEdad(new Edad(2));
        publicacionFiltros.setCompatibleCon(new CompatibleCon(1));
        publicacionFiltros.setVacunasAlDia(new VacunasAlDia(1));
        publicacionFiltros.setPapelesAlDia(new PapelesAlDia(1));
        publicacionFiltros.setCastrado(new Castrado(1));
        publicacionFiltros.setProteccion(new Proteccion(1));
        publicacionFiltros.setEnergia(new Energia(1));
        publicacionFiltros.setLatitud(10.0);
        publicacionFiltros.setLongitud(10.0);
        publicacionFiltros.setDistancia(10.0);
        publicacionFiltros.setNecesitaTransito(false);
        publicacionFiltros.setRequiereCuidadosEspeciales(false);

        List<Publicacion> publicaciones = Publicacion.buscarPublicaciones(usuario.getToken(), 0, 0, publicacionFiltros);
        assertTrue(publicaciones.size() == 0);
	}

    @Test
    public void buscarPublicacionesConTokenDistintoSiDevuelve() {
        crearPublicacion(Usuario.getInstancia().getToken());

        Publicacion publicacionFiltros = new Publicacion();
        publicacionFiltros.setEspecie(new Especie(1));
        publicacionFiltros.setLongitud(10.0);
        publicacionFiltros.setLatitud(15.0);
        publicacionFiltros.setDistancia(10.0);

        List<Publicacion> publicaciones = Publicacion.buscarPublicaciones("234567", 0, 0, publicacionFiltros);
        assertTrue(publicaciones.size() > 0);
    }

    @Test
    public void buscarPublicacionesConDistanciaNula() {
        crearPublicacion(Usuario.getInstancia().getToken());

        Publicacion publicacionFiltros = new Publicacion();
        publicacionFiltros.setEspecie(new Especie(1));
        publicacionFiltros.setLongitud(10.0);
        publicacionFiltros.setLatitud(15.0);
        publicacionFiltros.setDistancia(null);

        List<Publicacion> publicaciones = Publicacion.buscarPublicaciones("234567", 0, 0, publicacionFiltros);
        assertTrue(publicaciones.size() > 0);
    }

    @Test
    public void buscarPublicacionesConDistanciaNoNulaConDecimales() {
        crearPublicacion(Usuario.getInstancia().getToken());

        Publicacion publicacionFiltros = new Publicacion();
        publicacionFiltros.setEspecie(new Especie(1));
        publicacionFiltros.setLongitud(10.0);
        publicacionFiltros.setLatitud(15.0);
        publicacionFiltros.setDistancia(250.0);

        List<Publicacion> publicaciones = Publicacion.buscarPublicaciones("234567", 0, 0, publicacionFiltros);
        assertTrue(publicaciones.size() > 0);
    }

    @Test
	public void obtenerPublicacion() {
		Integer publicacionId = crearPublicacion(usuario.getToken());
        Publicacion publicacion = Publicacion.obtenerPublicacion(usuario.getToken(), publicacionId);
        assertTrue("El id de la publicacion no coincide con el guardado", publicacion.getId().equals(publicacionId));
	}

}
