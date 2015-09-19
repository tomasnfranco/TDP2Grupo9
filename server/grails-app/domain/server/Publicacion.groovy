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
	int latitud = 0
	int longitud = 0
	String nombreMascota = ''
	String condiciones = ''
	boolean requiereCuidadosEspeciales = false
	boolean necesitaTransito = false
	String videoLink
	
    static constraints = {
		
    }
}
