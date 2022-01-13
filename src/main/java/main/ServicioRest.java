package main;

import modeloDAO.IngresoUsuarios;
import servicios.ServerComandoWS;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServicioRest {

    public static int PORT = 1;
    public static String PUERTO_COM = "tty.usbserial-A50285BI";
    public static boolean SECURE_PROTOCOLO = false;
    public static String FORMATOFECHA = "yyyy-MM-dd";
    public static String url;

    public static void main(String[] args) throws UnknownHostException {
        if (args != null) {
            try {
                PORT = Integer.parseInt(args[0]);
                PUERTO_COM = args[1];
                SECURE_PROTOCOLO = Boolean.parseBoolean(args[1]);
            } catch (Exception s) {
                PORT = 10100;
            }
        }
        url = "http://localhost:" + PORT + "/api/estadotanque";
        if (SECURE_PROTOCOLO) {
            url = "https://localhost:" + PORT + "/api/estadotanque";
        }

        ServerComandoWS server = new ServerComandoWS();
        try {
            server.init();
        } catch (Exception e) {
            Logger.getLogger(IngresoUsuarios.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
