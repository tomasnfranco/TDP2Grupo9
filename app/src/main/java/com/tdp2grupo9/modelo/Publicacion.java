package com.tdp2grupo9.modelo;

import com.tdp2grupo9.modelo.publicacion.Castrado;
import com.tdp2grupo9.modelo.publicacion.Color;
import com.tdp2grupo9.modelo.publicacion.CompatibleCon;
import com.tdp2grupo9.modelo.publicacion.Edad;
import com.tdp2grupo9.modelo.publicacion.Energia;
import com.tdp2grupo9.modelo.publicacion.Especie;
import com.tdp2grupo9.modelo.publicacion.PapelesAlDia;
import com.tdp2grupo9.modelo.publicacion.Proteccion;
import com.tdp2grupo9.modelo.publicacion.Sexo;
import com.tdp2grupo9.modelo.publicacion.Tamanio;
import com.tdp2grupo9.modelo.publicacion.VacunasAlDia;

import java.util.ArrayList;
import java.util.List;

public class Publicacion {

    private Color color;
    private Castrado castrado;
    private Especie especie;
    private CompatibleCon compatibleCon;
    private Edad edad;
    private Energia energia;
    private PapelesAlDia papelesAlDia;
    private Proteccion proteccion;
    private Sexo sexo;
    private Tamanio tamanio;
    private VacunasAlDia vacunasAlDia;

    public Publicacion() {}

    public static List<Publicacion> buscarPublicaciones() {
        return new ArrayList<>();
    }

    public Castrado getCastrado() {
        return castrado;
    }

    public Color getColor() {
        return color;
    }

    public Edad getEdad() {
        return edad;
    }

    public Energia getEnergia() {
        return energia;
    }

    public Especie getEspecie() {
        return especie;
    }

    public CompatibleCon getCompatibleCon() {
        return compatibleCon;
    }

    public PapelesAlDia getPapelesAlDia() {
        return papelesAlDia;
    }

    public Proteccion getProteccion() {
        return proteccion;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public Tamanio getTamanio() {
        return tamanio;
    }

    public VacunasAlDia getVacunasAlDia() {
        return vacunasAlDia;
    }

    public void setCastrado(Castrado castrado) {
        this.castrado = castrado;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setCompatibleCon(CompatibleCon compatibleCon) {
        this.compatibleCon = compatibleCon;
    }

    public void setEdad(Edad edad) {
        this.edad = edad;
    }

    public void setEnergia(Energia energia) {
        this.energia = energia;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public void setPapelesAlDia(PapelesAlDia papelesAlDia) {
        this.papelesAlDia = papelesAlDia;
    }

    public void setProteccion(Proteccion proteccion) {
        this.proteccion = proteccion;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public void setTamanio(Tamanio tamanio) {
        this.tamanio = tamanio;
    }

    public void setVacunasAlDia(VacunasAlDia vacunasAlDia) {
        this.vacunasAlDia = vacunasAlDia;
    }

}
