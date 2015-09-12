package server

class Usuario {
	String username
	String email
	String password
	long facebookId
	String telefono
	String direccion
	long latitud
	long longitud
	boolean autoPublicar
	boolean activo
	long token
	
    static constraints = {
		username(nullable: true, blank:true)
		email(nullable:true, blank:true)
		password(nullable: true, blank:true)
		facebookId validator:{val,obj ->
			if(val == null || val == 0)
				return true
			else {
				if(Usuario.findAllByFacebookId(val).size() == 0)
					return true
				else {
					Usuario otro = Usuario.findByFacebookId(val)
					if(otro.id == obj.id){
						return true;
					}
					return false;
				}
			}
		}
    }
}
