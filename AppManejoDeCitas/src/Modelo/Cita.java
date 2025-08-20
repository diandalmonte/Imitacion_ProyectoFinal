package Modelo;
import java.time.LocalDateTime;
import java.util.List;

public class Cita {
    private int id;
    private String encabezado, descripcion;
    private List<Contacto> contactos;
    private LocalDateTime fecha_cita;

    public Cita(int id, String encabezado, String descripcion, List<Contacto> contactos, LocalDateTime fecha_cita){
        this.id = id;
        setEncabezado(encabezado);
        setDescripcion(descripcion);
        setContactos(contactos);
        setFecha_cita(fecha_cita);
    }

    public Cita(String encabezado, String descripcion, List<Contacto> contactos, LocalDateTime fecha_cita){
        setEncabezado(encabezado);
        setDescripcion(descripcion);
        setContactos(contactos);
        setFecha_cita(fecha_cita);
    }

    public String getEncabezado() {
        return encabezado;
    }

    public void setEncabezado(String encabezado) {
        if (validacionService.isStringNull(encabezado) && encabezado.length() <= 50){
            this.encabezado = encabezado;
        } //Issues: This doesnt do anything for when its not valid, also doesnt specify which isnt correct
        this.encabezado = encabezado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        if (validacionService.isStringNull(descripcion) && descripcion.length() <= 800){
            this.descripcion = descripcion;
        } //Issues: This doesnt do anything for when its not valid, also doesnt specify which isnt correct
    }

    public List<Contacto> getContactos() {
        return contactos;
    }

    public void setContactos(List<Contacto> contactos) {
        this.contactos = contactos;
    }

    public void addContacto(Contacto contacto){
        this.contactos.add(contacto);
    }

    public LocalDateTime getFecha() {
        return fecha_cita;
    }

    public void setFecha_cita(LocalDateTime fecha_cita) {
        if (validacionService.isFechaValid(fecha_cita)){
            this.fecha_cita = fecha_cita;
        } //Issues: This doesnt do anything for when its not valid
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    @Override
    public String toString(){
        return String.format("ID: %d Encabezado: %s Fecha: %t Contactos relacionados: %d Descripcion: %s", 
        getId(), getEncabezado(), getFecha(), getContactos().size(), getDescripcion());
    }

    
}