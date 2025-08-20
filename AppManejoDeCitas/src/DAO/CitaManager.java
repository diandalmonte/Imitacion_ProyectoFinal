package DAO;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import DAO.enums.CamposCita;
import Modelo.Cita;
import Modelo.Contacto;

public class CitaManager extends CrudManager<Cita, DAO.enums.CamposCita> {

    public CitaManager(ManagerDB db){
        super(db);
    }
    
    @Override
    public void guardar(Cita cita){
        String query = "INSERT INTO Citas(encabezado, fecha_cita, descripcion) " +
                        "VALUES (?, ?, ?)";
        try(Connection connection = db.conectar();//!!!!!Consider what to do here instead of hardcoding that  eliminar has admin
            PreparedStatement pStatement = connection.prepareStatement(query)){

            pStatement.setString(1, cita.getEncabezado());
            pStatement.setTimestamp(2, Timestamp.valueOf(cita.getFecha()));
            pStatement.setString(3, cita.getDescripcion());

            pStatement.executeUpdate();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void guardar(Cita cita, List<Contacto> contactos){ //Metodo para crear cita asociada a contactos
        String query = "INSERT INTO Citas(encabezado, fecha_cita, descripcion) " +
                        "VALUES (?, ?, ?) ";
        String queryRelacion = "INSERT INTO Citas_Contactos(id_cita, id_contacto) " +
                                "VALUES (?, ?)";
        try(Connection connection = db.conectar();//!!!!!Consider what to do here instead of hardcoding that  eliminar has admin
            PreparedStatement pStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){

            pStatement.setString(1, cita.getEncabezado());
            pStatement.setTimestamp(2, Timestamp.valueOf(cita.getFecha()));
            pStatement.setString(3, cita.getDescripcion());

            pStatement.executeUpdate();

            try (ResultSet rSet = pStatement.getGeneratedKeys()){
                if (rSet.next()){
                    int idCita = rSet.getInt(1);
                    cita.setId(idCita);
                } else {
                    throw new SQLException("No se generó id_cita");
                }
            }

            try(PreparedStatement psRelacion = connection.prepareStatement(queryRelacion)){
                for (Contacto contacto : contactos){
                psRelacion.setInt(1, cita.getId());
                psRelacion.setInt(2, contacto.getId());
                psRelacion.executeUpdate();

            }
            }


        } catch (Exception e){
            e.printStackTrace();
        }

    }


    @Override
    public void actualizar(int id, CamposCita campo, Object valor){
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

    public List<Cita> obtener(){
        String query = "SELECT id_cita, encabezado, fecha_cita, descripcion FROM Citas";
        //Options to consider, hashMap??
        List<Cita> resultados = new ArrayList<>();
        try (Connection connection = db.conectar(); //!!!!!Consider what to do here instead of hardcoding that eliminar has admin
            
            PreparedStatement pStatement = connection.prepareStatement(query);
            ResultSet rSet = pStatement.executeQuery()){

            while (rSet.next()){
                int id = rSet.getInt("id_cita");
                String encabezado = rSet.getString("encabezado");
                // java.sql.ResultSet no tiene metodo getLocalDateTime(), entonces tomamos Timestamp y luego lo convertimos a LocalDateTime
                LocalDateTime fecha = rSet.getTimestamp("fecha_cita").toLocalDateTime();
                String descripcion = rSet.getString("descripcion");

                Cita cita = new Cita(id, encabezado, descripcion, new ArrayList<>(), fecha);
                
                resultados.add(cita);
            }
            

        } catch (Exception e){
            e.printStackTrace();
        }
        
        String queryContactosCita = "SELECT cc.id_cita, cc.id_contacto, c.nombre, c.apellido, c.empresa, c.telefono, c.correo " +
                                    "FROM Citas_Contactos cc " +
                                    "JOIN Contactos c ON cc.id_contacto = c.id_contacto;";
                            
        try (Connection connection = db.conectar(); //!!!!!Consider what to do here instead of hardcoding that eliminar has admin
            PreparedStatement pStatement = connection.prepareStatement(queryContactosCita);
            ResultSet rSet = pStatement.executeQuery()){

            while (rSet.next()){
                int id_cita = rSet.getInt("id_cita");
                int id_contacto = rSet.getInt("id_contacto");
                String nombre = rSet.getString("nombre");
                String apellido = rSet.getString("apellido");
                String empresa = rSet.getString("empresa");
                String telefono = rSet.getString("telefono");
                String correo = rSet.getString("correo");

                //This is not very efficient, consider hashMap??
                for (int i = 0; i < resultados.size(); i++){
                    if (resultados.get(i).getId() == id_cita){
                        resultados.get(i).addContacto(new Contacto(id_contacto, nombre, apellido, empresa, telefono, correo));
                    }
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return resultados;
    }
    
}
