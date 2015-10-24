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

    public static TipoPublicacion getTipoPublicacion(String tipo) {
        switch (tipo) {
            case "Adopciones":
                return TipoPublicacion.ADOPCION;
            case "Perdidos":
                return TipoPublicacion.PERDIDA;
            case "Encontrados":
                return TipoPublicacion.ENCONTRADA;
            default:
                return TipoPublicacion.ADOPCION;
        }
    }

    public static String getTipoPublicacionToString(int tipo) {
        switch (tipo) {
            case 1:
                return "Adopciones";
            case 2:
                return "Perdidos";
            case 3:
                return "Encontrados";
            default:
                return "Adopciones";
        }
    }
}
