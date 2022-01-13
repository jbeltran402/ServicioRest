package main;

import modeloDAO.IngresoUsuarios;
import servicios.ServerComandoWS;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServicioRest {

    public static String FORMATOFECHA = "yyyy-MM-dd";

    public static void main(String[] args) throws UnknownHostException {
        ServerComandoWS server = new ServerComandoWS();
        try {
            server.init();
        } catch (Exception e) {
            Logger.getLogger(IngresoUsuarios.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
