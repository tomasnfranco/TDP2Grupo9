package com.tdp2grupo9.drawer;

public enum DrawerMenuAction {
    MIS_PUBLIACIONES(1), MIS_POSTULACIONES(2), RESULTADO_BUSQUEDA(3), RECIENTES(4), MIS_ALERTAS(5);

    private final int value;

    DrawerMenuAction(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static DrawerMenuAction getMenuAction(int value) {
        switch (value) {
            case 1:
                return MIS_PUBLIACIONES;
            case 2:
                return MIS_POSTULACIONES;
            case 3:
                return RESULTADO_BUSQUEDA;
            case 4:
                return RECIENTES;
            case 5:
                return MIS_ALERTAS;
            default:
                return null;
        }
    }

    public static DrawerMenuAction getMenuAction(String value) {
        switch (value) {
            case "MIS PUBLICACIONES":
                return MIS_PUBLIACIONES;
            case "MIS POSTULACIONES":
                return MIS_POSTULACIONES;
            case "RESULTADO BUSQUEDA":
                return RESULTADO_BUSQUEDA;
            case "RECIENTES":
                return RECIENTES;
            case "MIS ALERTAS":
                return MIS_ALERTAS;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        switch (value) {
            case 1:
                return "MIS PUBLICACIONES";
            case 2:
                return "MIS POSTULACIONES";
            case 3:
                return "RESULTADO BUSQUEDA";
            case 4:
                return "RECIENTES";
            case 5:
                return "MIS ALERTAS";
            default:
                return "";
        }
    }
}
