package DAO;
import java.util.List;

import DAO.enums.IdEnum;
import DAO.enums.TablasBD;

import java.sql.*;

public abstract class CrudManager<T, E extends Enum<E>> {
    ManagerDB db;

    public CrudManager(ManagerDB db){
        this.db = db;
    }

    public abstract void guardar(T obj);

    public abstract void actualizar(int id, E campo, Object valor);

    public abstract List<T> obtener();

    public void eliminar(int id, TablasBD tabla, IdEnum idTabla){
        String query = String.format("DELETE FROM %s WHERE %s = ?", tabla.getValor(), idTabla.getValor());
        try(Connection connection = db.conectar(); //Consider what to do here instead of hardcoding that  eliminar has admin

            PreparedStatement pStatement = connection.prepareStatement(query)){
            pStatement.setInt(1, id);

            pStatement.executeUpdate();

        } catch (Exception e){
            e.printStackTrace();
        }
        

    }
}
