package main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class ServerComandoWS {

    int PORT = 10100;
    public static Gson gson = new GsonBuilder().setDateFormat(ServicioRest.FORMATOFECHA).setPrettyPrinting().create();

    public void init() throws Exception {
        System.out.println("INICIANDO SERVER WS :" + PORT);

        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/api", new HttpHandlerApi());
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

                System.out.println("\n Resepcion de Venta - Json \n");
                JsonArray recepcionDatos = gson.fromJson(buf.toString(), JsonArray.class);

                for (JsonElement jUsuarios : recepcionDatos) {
                    Transaccion con = gson.fromJson(jUsuarios.toString(), Transaccion.class);
                    if (Dao.verificarProducto(con.getVenta_id(), con.getPos_id()) == false) {

                    }else{
                        System.out.println("");
                    }
                }

                JsonObject json = new JsonObject();
                json.addProperty("success", true);
                json.addProperty("mensaje", "ACTUALIZACION DE DATOS CORRECTA");
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
                respHeaders.add("content-type", "application/json/combustibles");
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
                respHeaders.add("content-type", "application/json");
                t.sendResponseHeaders(500, response.getBytes("utf-8").length);
                try (OutputStream os = t.getResponseBody()) {
                    os.write(response.getBytes());
                    os.flush();
                }
            }
        }
    }
}
