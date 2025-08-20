package DAO.enums;

public enum IdEnum{
    CITA("id_cita"),
    CONTACTO("id_contacto");

    private final String valor;

    IdEnum(String valor){
        this.valor = valor;
    }

    public String getValor(){
        return valor;
    }  
}
