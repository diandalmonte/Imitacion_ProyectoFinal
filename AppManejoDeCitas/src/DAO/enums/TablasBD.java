package DAO.enums;

public enum TablasBD{
    CITA("Citas"),
    CONTACTO("Contactos"),
    ID_CITA("id_cita"),
    ID_CONTACTO("id_contacto");

    private final String nombreTabla;

    TablasBD(String nombreTabla){
        this.nombreTabla = nombreTabla;
    }

    public String getValor(){
        return  nombreTabla;
    }
}
