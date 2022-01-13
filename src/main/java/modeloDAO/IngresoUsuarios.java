package modeloDAO;

import config.ConexionDB;
import main.ServicioRest;
import modelo.Fecha;
import modelo.Usuarios;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IngresoUsuarios implements interfaces.IngresoUsuarios{

    ConexionDB cn = new ConexionDB();
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
    LocalDate fechaActual = LocalDate.now();
    Fecha fecha = new Fecha();

    public static java.text.SimpleDateFormat SimpleDateFormat = new SimpleDateFormat(ServicioRest.FORMATOFECHA);

    @Override
    public List AnadirUsuario(Usuarios usuarios) {

        ArrayList<IngresoUsuarios> list = new ArrayList<>();
        String sql = "INSERT INTO servicio_rest.usuarios VALUES (default,?,?,?,?,?,?,?,?);";
        try {
            conn = cn.conectar();
            ps = conn.prepareStatement(sql);
            ps.setString(1, usuarios.getNombres().toLowerCase());
            ps.setString(2, usuarios.getApellidos().toLowerCase());
            ps.setString(3, usuarios.getTipoDocumento().toUpperCase());
            ps.setString(4, usuarios.getNumerodocumento());
            ps.setDate(5, new java.sql.Date(usuarios.getFechaNacimiento().getTime()));
            ps.setDate(6, new java.sql.Date(usuarios.getFechaVinculacion().getTime()));
            ps.setString(7, usuarios.getCargo().toLowerCase());
            ps.setDouble(8, usuarios.getSalario());
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e + " ERROR EN LA INYECCION SQL en AnadirUsuario");
            Logger.getLogger(IngresoUsuarios.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            Desconectar();
        }
        return list;
    }

    @Override
    public boolean VerificarContenido(Usuarios usuarios) {
        boolean estado = true;
        if (usuarios.getNombres().isEmpty() || usuarios.getApellidos().isEmpty() ||
                usuarios.getTipoDocumento().isEmpty() || usuarios.getTipoDocumento().isEmpty() ||
                usuarios.getCargo().isEmpty() || usuarios.getSalario() <= 0)
        {
            estado = false;
        }
        return estado;
    }

    @Override
    public List edad(Date fechaNacimiento) {

        LocalDate FechaNacimiento = LocalDate.parse(SimpleDateFormat.format(fechaNacimiento), DateTimeFormatter.ISO_LOCAL_DATE);
        Period period = Period.between(fechaActual, FechaNacimiento);


        ArrayList<Fecha> list = new ArrayList<>();
        fecha.setDia(Math.abs(period.getDays()));
        fecha.setMes(Math.abs(period.getMonths()));
        fecha.setAnio(Math.abs(period.getYears()));
        list.add(fecha);

        return list;
    }

    @Override
    public List tiempoDeVinculacion(Date fechaVinculacion) {

        LocalDate FechaVinculacion = LocalDate.parse(SimpleDateFormat.format(fechaVinculacion), DateTimeFormatter.ISO_LOCAL_DATE);
        Period period = Period.between(fechaActual, FechaVinculacion);
        ArrayList<Fecha> list = new ArrayList<>();
        fecha.setMes(Math.abs(period.getMonths()));
        fecha.setAnio(Math.abs(period.getYears()));
        list.add(fecha);

        return list;
    }

    @Override
    public boolean Desconectar() {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
