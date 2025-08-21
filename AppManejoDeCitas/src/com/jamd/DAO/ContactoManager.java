package com.jamd.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.jamd.DAO.enums.CamposContacto;
import com.jamd.Modelo.Contacto;

public class ContactoManager extends CrudManager<Contacto, CamposContacto>{
    
    public ContactoManager(ManagerDB db){
        super(db);
    }
    
    @Override
    public void guardar(Contacto contacto){
        String query = "INSERT INTO Contactos(nombre, apellido, empresa, telefono, correo) " +
                        "VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = db.conectar();//!!!!!Consider what to do here instead of hardcoding that  eliminar has admin
            PreparedStatement pStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){

            pStatement.setString(1, contacto.getNombre());
            pStatement.setString(2, contacto.getApellido());
            pStatement.setString(3, contacto.getEmpresa());
            pStatement.setString(4, contacto.getTelefono());
            pStatement.setString(5, contacto.getCorreo());

            pStatement.executeUpdate();

            try (ResultSet rSet = pStatement.getGeneratedKeys()){
                if (rSet.next()){
                int id_contacto = rSet.getInt(1);
                contacto.setId(id_contacto);
                } else {
                    throw new SQLException("No se generó id_cita");
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        
    }

    @Override
    public void actualizar(int id, CamposContacto campo, Object valor){
        String query = "UPDATE Contactos SET " + campo.getValor() + " = ? WHERE id_contacto = ?";
        try(Connection connection = db.conectar();
            PreparedStatement pStatement = connection.prepareStatement(query)){

            pStatement.setObject(1, valor);
            pStatement.setInt(2, id);

            pStatement.executeUpdate();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<Contacto> obtener(){
        String query = "SELECT id_contacto, nombre, apellido, empresa, telefono, correo FROM Contactos";
        List<Contacto> resultados = new ArrayList<>();
        try(Connection connection = db.conectar(); 
            
            PreparedStatement pStatement = connection.prepareStatement(query);
            ResultSet rSet = pStatement.executeQuery()){

            while (rSet.next()){
                int id = rSet.getInt("id_contacto");
                String nombre = rSet.getString("nombre");
                String apellido = rSet.getString("apellido");
                String empresa = rSet.getString("empresa");
                String telefono = rSet.getString("telefono");
                String correo = rSet.getString("correo");

                Contacto contacto = new Contacto(id, nombre, apellido, empresa, telefono, correo);
                resultados.add(contacto);
                
            }
            
            return resultados;

        } catch (Exception e){
            e.printStackTrace();
            return resultados;
        }
    } 

    public int findId(String correo) throws NoSuchElementException, SQLException{
        String query = "SELECT id_contacto FROM Contactos " +
                        "WHERE correo = ?";
        try (Connection connection = db.conectar();
            PreparedStatement pStatement = connection.prepareStatement(query)){

            pStatement.setString(1, correo);
            try (ResultSet rSet =  pStatement.executeQuery()){
                if (rSet.next()){
                    return rSet.getInt("id_contacto");
                } else{
                    throw new NoSuchElementException("Correo: " + correo + " no se pudo encontrar");
                }
            }
        
            } catch (SQLException e){
                e.printStackTrace();
                throw e;
            }
    }
}