package server
import groovy.transform.ToString

@ToString(includeNames = true, includeFields = true, excludes = 'metaClass,class',ignoreNulls=true)
class Usuario {
	String username
	String email
	String password
	long facebookId
	String telefono
	String direccion
	long latitud = 0
	long longitud = 0
	boolean autoPublicar
	boolean activo = true
	String token = ""
	
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
		token (blank:true, nullable:true)
    }
	
	def generator = { String alphabet, int n ->
	  new Random().with {
		(1..n).collect { alphabet[ nextInt( alphabet.length() ) ] }.join()
	  }
	}

	def generarToken = {
		token = generator( (('A'..'Z')+('0'..'9')).join(), 15 )
	}
}
