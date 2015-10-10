package server
import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

class Publicacion {
	Date fechaPublicacion = new Date()
	boolean activa = true
	Usuario publicador
	Usuario concretado = null

	//Atributos de Busqueda
	Castrado castrado
	Color color
	CompatibleCon compatibleCon
	Edad edad
	Energia energia
	Especie especie
	PapelesAlDia papelesAlDia
	Proteccion proteccion
	Raza raza
	Sexo sexo
	Tamanio tamanio
	VacunasAlDia vacunasAlDia
	//Fin Atributos de Busqueda
	int tipoPublicacion = 1
	/**
	 * Tipos Publicacion
	 * 1 = Publicación de mascota en adopción
	 */
	double latitud = 0
	double longitud = 0
	String nombreMascota = ' '
	String condiciones = ' '
	boolean requiereCuidadosEspeciales = false
	boolean necesitaTransito = false
	String videoLink = ' '
	double distancia = 0
	static transients = ['distancia']
	static hasMany = [fotos : Foto, quierenAdoptar : Usuario, preguntas : Mensaje]
	
    static constraints = {
		publicador()
		condiciones(blank: true,nullable: true)
		nombreMascota(blank:true,nullable: true)
		videoLink(blank:true,nullable: true)
		fotos(maxSize: 6)
		concretado nullable: true
    }

	static marshalling = {
		virtual {
			distancia { value, json -> json.value(value.distancia) }
		}
		deep 'fotos'
	}

	double setDistancia(def lat,def lon){
		distancia = LatLngTool.distance(new LatLng(this.latitud, this.longitud),new LatLng(lat, lon),LengthUnit.KILOMETER)
	}
}
