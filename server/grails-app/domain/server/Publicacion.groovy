package server
import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

class Publicacion {
	Date fechaPublicacion = new Date()
	boolean activa = true
	Usuario publicador
	Usuario concretado = null
	Usuario transito = null

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
	 * 2 = Publicación de mascota perdida
	 * 3 = Publicación de mascota encontrada
	 */
	double latitud = 0
	double longitud = 0
	String nombreMascota = ' '
	String condiciones = ' '
	String direccion = ' '
	boolean requiereCuidadosEspeciales = false
	boolean necesitaTransito = false
	String videoLink = ' '
	double distancia = 0
	static transients = ['distancia']
	static hasMany = [fotos : Foto, quierenAdoptar : Usuario, preguntas : Mensaje, ofrecenTransito: Usuario]
	
    static constraints = {
		publicador()
		condiciones(blank: true,nullable: true)
		nombreMascota(blank:true,nullable: true)
		videoLink(blank:true,nullable: true)
		fotos(maxSize: 6)
		concretado nullable: true
		transito nullable: true
		direccion nullable:true, blank:true
    }

	static marshalling = {
		virtual {
			distancia { value, json -> json.value(value.distancia) }
			publicadorNombre {value,json -> json.value(value.publicador?.username ?: '')}
			publicadorId {value,json -> json.value(value.publicador?.id ?: '')}
			direccionTransito {value,json -> json.value(value.transito?.direccion ?: '')}
			longitudTransito {value,json -> json.value(value.transito?.longitud?: '')}
			latitudTransito {value,json -> json.value(value.transito?.latitud ?: '')}
			transitoNombre {value,json -> json.value(value.transito?.username ?: '')}
			transitoId {value,json -> json.value(value.transito?.id ?: '')}
			concretadoNombre {value,json -> json.value(value.concretado?.username ?: '')}
			concretadoId {value,json -> json.value(value.concretado?.id ?: '')}
		}
		deep 'fotos'
	}

	double setDistancia(def lat,def lon){
		distancia = LatLngTool.distance(new LatLng(this.latitud, this.longitud),new LatLng(lat, lon),LengthUnit.KILOMETER)
	}
}
