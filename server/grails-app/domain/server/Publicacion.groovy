package server

class Publicacion {
	Date fechaPublicacion = new Date()
	boolean activa = true
	Usuario publicador
	//Atributos de Busqueda
	Castrado castrado
	Color color
	CompatibleCon compatibleCon
	Edad edad
	Energia energia
	Especie especie
	PapelesAlDia papelesAlDia
	Proteccion proteccion
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
	static hasMany = [fotos : Foto]
	
    static constraints = {
		publicador()
		condiciones(blank: true,nullable: true)
		nombreMascota(blank:true,nullable: true)
		videoLink(blank:true,nullable: true)
    }
}
