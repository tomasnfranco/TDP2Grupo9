package com.tdp2grupo9.modelo;

public enum TipoNotificacion {
    POSTULACION(1), CONCRETADA(2), CANCELADA(3), PREGPUBLICA(4), RESPPUBLICA(5), PREGPRIVADA(6), RESPPRIVADA(7), ALERTA(8);

    private final int value;

    TipoNotificacion(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static TipoNotificacion getTipoPublicacion(int tipo) {
        switch (tipo) {
            case 1:
                return TipoNotificacion.POSTULACION;
            case 2:
                return TipoNotificacion.CONCRETADA;
            case 3:
                return TipoNotificacion.CANCELADA;
            case 4:
                return TipoNotificacion.PREGPUBLICA;
            case 5:
                return TipoNotificacion.RESPPUBLICA;
            case 6:
                return TipoNotificacion.PREGPRIVADA;
            case 7:
                return TipoNotificacion.RESPPRIVADA;
            case 8:
                return TipoNotificacion.ALERTA;
            default:
                return null;
        }
    }
}
