package com.jamd.DAO.enums;

public enum IdEnum{
    ID_CITA("id_cita"),
    ID_CONTACTO("id_contacto");

    private final String valor;

    IdEnum(String valor){
        this.valor = valor;
    }

    public String getValor(){
        return valor;
    }  
}
