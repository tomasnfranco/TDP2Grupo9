package server

class Alerta {
    Usuario usuario
    Date fechaCreacion = new Date()
    String nombre = ""
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
    double latitud = 0
    double longitud = 0
    double distancia = 50
    boolean requiereCuidadosEspeciales = false
    boolean necesitaTransito = false
    int tipoPublicacion = 1

    static constraints = {
        usuario nullable: false
        castrado nullable: true
        compatibleCon nullable: true
        energia nullable: true
        papelesAlDia nullable: true
        proteccion nullable: true
        sexo nullable: true
        tamanio nullable: true
        vacunasAlDia nullable: true
        raza nullable:true
        color nullable: true
        edad nullable: true
    }
}
