package servicios;

import com.google.gson.*;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import main.ServicioRest;
import modelo.Fecha;
import modelo.Usuarios;
import modeloDAO.IngresoUsuarios;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerComandoWS {

    int PORT = 10100;
    public static Gson gson = new GsonBuilder().setDateFormat(ServicioRest.FORMATOFECHA).setPrettyPrinting().create();
    IngresoUsuarios ingresoUsuarios = new IngresoUsuarios();

    public void init() throws Exception {
        System.out.println("INICIANDO SERVER WS :" + PORT);

        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/api/usuarios", new HttpHandlerApi());
        server.createContext("/api/ping", new HttpHandlerPing());

        server.setExecutor(null);
        server.start();
    }

    private class HttpHandlerApi implements HttpHandler {

        @Override
        public void handle(HttpExchange t) throws IOException {

            String method = t.getRequestMethod();

            if (method.equalsIgnoreCase("POST")) {

                Headers outHeaders = t.getResponseHeaders();
                outHeaders.set("Context-Type", "application/json/usuarios");

                InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
                BufferedReader br = new BufferedReader(isr);

                int b;
                StringBuilder buf = new StringBuilder();
                while ((b = br.read()) != -1) {
                    buf.append((char) b);
                }

                System.out.println("\n Resepcion de Usuario - Json \n");

                JsonArray recepcionDatos = gson.fromJson(buf.toString(), JsonArray.class);
                JsonObject json = new JsonObject();
                JsonObject jsonVinculacion = new JsonObject();
                JsonObject jsonEdad = new JsonObject();
                int edadUsuario = 0;

                try {

                    for (JsonElement jUsuarios : recepcionDatos) {

                        Usuarios con = gson.fromJson(jUsuarios.toString(), Usuarios.class);
                        List<Fecha> list = ingresoUsuarios.edad(con.getFechaNacimiento());
                        for (Fecha edad : list) {
                            jsonEdad.addProperty("anio", edad.getAnio());
                            jsonEdad.addProperty("mes", edad.getMes());
                            jsonEdad.addProperty("dia", edad.getDia());
                            edadUsuario = edad.getAnio();
                        }
                            if(!ingresoUsuarios.VerificarContenido(con) || edadUsuario < 18){
                                json.addProperty("success", true);
                                json.addProperty("mensaje", "DATOS ERRONEOS");
                            }else{
                                ingresoUsuarios.AnadirUsuario(con);
                                json.addProperty("success", true);
                                List<Fecha> listVinculacion = ingresoUsuarios.tiempoDeVinculacion(con.getFechaVinculacion());
                                for (Fecha tiempoVinculacion : listVinculacion) {
                                    jsonVinculacion.addProperty("anio", tiempoVinculacion.getAnio());
                                    jsonVinculacion.addProperty("mes", tiempoVinculacion.getMes());
                                }
                                json.add("vinculacion", jsonVinculacion);
                                json.add("edad", jsonEdad);
                            }
                        }
                }catch (Exception e){
                    Logger.getLogger(IngresoUsuarios.class.getName()).log(Level.SEVERE, null, e);
                    json.addProperty("success", true);
                    json.addProperty("mensaje", "DATOS ERRONEOS");
                }
                    String response = json.toString();
                    Headers respHeaders = t.getResponseHeaders();
                    respHeaders.add("content-type", "application/json");
                    t.sendResponseHeaders(200, response.getBytes("utf-8").length);
                    try (OutputStream os = t.getResponseBody()) {
                        os.write(response.getBytes());
                        os.flush();
                    }

            } else {
                JsonObject json = new JsonObject();
                json.addProperty("success", false);
                json.addProperty("mensaje", "ERROR AL CONECTAR");
                String response = json.toString();

                Headers respHeaders = t.getResponseHeaders();
                respHeaders.add("content-type", "application/json");
                t.sendResponseHeaders(200, response.getBytes("utf-8").length);
                try (OutputStream os = t.getResponseBody()) {
                    os.write(response.getBytes());
                    os.flush();
                }
            }
        }
    }

    private class HttpHandlerPing implements HttpHandler {

        @Override

        public void handle(HttpExchange t) throws IOException {

            String method = t.getRequestMethod();

            if (method.equalsIgnoreCase("GET")) {

                JsonObject json = new JsonObject();
                json.addProperty("success", true);
                String response = json.toString();

                Headers respHeaders = t.getResponseHeaders();
                respHeaders.add("content-type", "application/json/ping");
                t.sendResponseHeaders(200, response.getBytes("utf-8").length);

                try (OutputStream os = t.getResponseBody()) {
                    os.write(response.getBytes());
                    os.flush();
                }
            } else {

                JsonObject json = new JsonObject();
                json.addProperty("success", false);
                String response = json.toString();

                Headers respHeaders = t.getResponseHeaders();
                respHeaders.add("content-type", "application/json/ping");
                t.sendResponseHeaders(500, response.getBytes("utf-8").length);
                try (OutputStream os = t.getResponseBody()) {
                    os.write(response.getBytes());
                    os.flush();
                }
            }
        }
    }
}
