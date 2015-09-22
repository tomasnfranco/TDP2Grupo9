package com.tdp2grupo9.listview;

public class Item {

    private int image;
    private String title;
    private Boolean cuidado_especial;
    private Boolean hogar_transito;

    public Item() {
        super();
    }

    public Item(int image, String title, Boolean cuidado_especial, Boolean hogar_transito) {
        super();
        this.image = image;
        this.title = title;
        this.cuidado_especial = cuidado_especial;
        this.hogar_transito = hogar_transito;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean requiereCuidadosEspeciales() {
        return this.cuidado_especial;
    }

    public Boolean requiereHogarDeTransito() {
        return this.hogar_transito;
    }

    public void setCuidadoEspecial(Boolean cuidado_especial) {
        this.cuidado_especial = cuidado_especial;
    }

    public void setHogartransito(Boolean hogar_transito) {
        this.hogar_transito = hogar_transito;
    }

}