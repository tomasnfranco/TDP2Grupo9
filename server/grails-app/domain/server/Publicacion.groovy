package server

class Publicacion {
	Date fechaPublicacion
	boolean activa
	Usuario publicador
	int tipoMascota
	int tipoPublicacion
	boolean necesitaTransito
	String videoLink
	
    static constraints = {
		
    }
}
