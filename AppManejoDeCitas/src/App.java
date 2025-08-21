
/*import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;*/
import java.util.List;

import com.jamd.DAO.CitaManager;
import com.jamd.DAO.ContactoManager;
import com.jamd.DAO.ManagerDB;
//import com.jamd.DAO.enums.CamposCita;
import com.jamd.DAO.enums.CamposContacto;
//import com.jamd.DAO.enums.IdEnum;
//import com.jamd.DAO.enums.TablasBD;
import com.jamd.Modelo.Cita;
import com.jamd.Modelo.Contacto;

public class App { //Esta clase es un desorden, no es parte del programa final, solo es usada para probar cosas.
    public static void main(String[] args) throws Exception {
        /* */
        ManagerDB db = new ManagerDB();

        ContactoManager contactoManager = new ContactoManager(db);
        /*Contacto contacto1, contacto2, contacto3;

        /*contactoManager.guardar( contacto1 = new Contacto("Pedro", "Rica", "8095396960", "pedro@gmail.com");
        /*contactoManager.guardar( contacto2 = new Contacto("Pedrito", "Gonzalez", "Rica", "8095396960", "pedritognz@gmail.com");
        /*contactoManager.guardar( contacto3 = new Contacto("Tulio", "Carrion",  "Rica", "8095396960", "tcarrion83@gmail.com");

        List<Contacto> contactos = new ArrayList<>();
        contactos.add(contacto1);
        contactos.add(contacto2);
        contactos.add(contacto3);

        for(Contacto contacto : contactos){
            contacto.setId(contactoManager.findId(contacto.getCorreo()));
        }

         */
        CitaManager citaManager = new CitaManager(db);
        /*citaManager.eliminar(1, TablasBD.CITA, IdEnum.ID_CITA); //Eliminar de tabla cita WHERE id_cita = 1
        citaManager.guardar(new Cita("Reunion con Rica Marketing", "Segunda reunion porque la primera fue en latin, y esa lengua esta muerta",
        contactos, LocalDateTime.of(2025, 8, 30, 13, 53,00)), contactos);
        */

        //COSAS PROBADAS: Obtener(), guardar(), actualizar() de ambos cita y contacto, eliminar() solo de Cita,


        List<Cita> citasDB = citaManager.obtener();

        for (Cita cita : citasDB){
            System.out.println(cita.toString());
        }

        List<Contacto> contactosDB = contactoManager.obtener();

        for (Contacto contacto : contactosDB){
            System.out.println(contacto.toString());
        }

        contactoManager.actualizar(2, CamposContacto.TELEFONO, "18093339999");

        //citasDB = citaManager.obtener();

        contactoManager.eliminar(3);
        
        contactosDB = contactoManager.obtener();

        for (Contacto contacto : contactosDB){
            System.out.println(contacto.toString());
        }

        // STRONGLY CONSIDER: DO I WANT TO BE ABLE TO DELETE A CONTACT RELATED TO AN APPOINTMENT WITHOUT DELETING OR MODIFYING SAID APPOINMENT? 
        //IF NO THEN, LAST COMMIT CHANGES LIKE hasForeignKey and changes to Contacto Manager's eliminar() arent valid
    }
}
