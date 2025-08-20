
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import DAO.CitaManager;
//import DAO.ContactoManager;
import DAO.ManagerDB;
import DAO.enums.IdEnum;
import DAO.enums.TablasBD;
import Modelo.Cita;
import Modelo.Contacto;

public class App {
    public static void main(String[] args) throws Exception {
        /* */
        ManagerDB db = new ManagerDB();

        //ContactoManager contactoManager = new ContactoManager(db);
        Contacto contacto1, contacto2, contacto3;

        /*contactoManager.guardar(*/ contacto1 = new Contacto("Pedro", "Rica", "8095396960", "pedro@gmail.com");
        /*contactoManager.guardar(*/ contacto2 = new Contacto("Pedrito", "Gonzalez", "Rica", "8095396960", "pedritognz@gmail.com");
        /*contactoManager.guardar(*/ contacto3 = new Contacto("Tulio", "Carrion",  "Rica", "8095396960", "tcarrion83@gmail.com");

        List<Contacto> contactos = new ArrayList<>();
        contactos.add(contacto1);
        contactos.add(contacto2);
        contactos.add(contacto3);

        
        CitaManager citaManager = new CitaManager(db);
        citaManager.eliminar(1, TablasBD.CITA, IdEnum.CITA);
        citaManager.guardar(new Cita("Reunion con Rica", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
        contactos, LocalDateTime.of(2025, 8, 28, 13, 53,00)), contactos);
        
        List<Cita> citas = citaManager.obtener();

        for (Cita cita : citas){
            System.out.printf(cita.toString());
        }

    }
}
