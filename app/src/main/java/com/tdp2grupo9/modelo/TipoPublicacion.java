package com.tdp2grupo9.modelo;

public enum TipoPublicacion {

    ADOPCION(1), PERDIDA(2), ENCONTRADA(3);

    private final int value;

    private TipoPublicacion(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static TipoPublicacion getTipoPublicacion(int tipo) {
        switch (tipo) {
            case 1:
                return TipoPublicacion.ADOPCION;
            case 2:
                return TipoPublicacion.PERDIDA;
            case 3:
                return TipoPublicacion.ENCONTRADA;
            default:
                return TipoPublicacion.ADOPCION;
        }
    }
}
