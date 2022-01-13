package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

        Connection conn;

    public Connection conectar() {
        conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/servicio_rest","root","12345678");
            //System.out.println("conexion satisfactoria");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println( e +" -> Conexion erronea");
        }
        return conn;
    }
}
