package com.tdp2grupo9.modelo.publicacion;

public abstract class AtributoPublicacion {

    protected int id;
    protected String valor;

    public AtributoPublicacion() {
        id = 0;
        valor = "DESCONOCIDO";
    }

    public AtributoPublicacion(Integer id) {
        this.id = id;
        valor = "DESCONOCIDO";
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public String getValor() {
        return this.valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return this.valor;
    }

    @Override
    public boolean equals(Object o) {
        return this.id == ((AtributoPublicacion) o).id;
    }

    public abstract String getName() ;
}
