package server

class Usuario {
	String username = ""
	String email = ""
	String password = ""
	long facebookId = 0
	String telefono = ""
	String direccion = ""
	double latitud = 0
	double longitud = 0
	boolean autoPublicar = false
	boolean ofreceTransito = false
	boolean activo = true
	String token = ""
	String foto = ""

	static mapping = {
		foto type: 'text'
	}

    static constraints = {
		username(nullable: true, blank:true)
		email(nullable:true, blank:true,email:true, validator:{val,obj ->
			if(val == null || val == '')
				return true
			else {
				Usuario otro = Usuario.findByEmail(val)
				if(otro == null)
					return true;
				if(otro.id == obj.id){
					return true;
				}
				return false;
			}
		})
		password(nullable: true, blank:true,password:true)
		facebookId validator:{val,obj ->
			if(val == null || val == 0)
				return true
			else {
				Usuario otro = Usuario.findByFacebookId(val)
				if(otro == null)
					return true;
				if(otro.id == obj.id){
					return true;
				}
				return false;
			}
		}
		token (blank:true, nullable:true)
		telefono (blank:false)
		direccion (blank:false)
		foto blank:true, nullable: true
    }
	
	def generator = { String alphabet, int n ->
	  new Random().with {
		(1..n).collect { alphabet[ nextInt( alphabet.length() ) ] }.join()
	  }
	}

	def generarToken = {
		while(true) {
			def tokenGenerado = generator((('A'..'Z') + ('0'..'9')).join(), 15)
			def user = Usuario.findByToken(tokenGenerado)
			if(user == null) {
				token = tokenGenerado
				return
			}
		}
	}

	String toString(){
		return username ?: ''
	}
}
