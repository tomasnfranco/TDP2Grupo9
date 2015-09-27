package com.tdp2grupo9.modelo.publicacion;

public class AtributoPublicacion {

    protected int id;
    protected String valor;

    public AtributoPublicacion() {
        id = 0;
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

}
