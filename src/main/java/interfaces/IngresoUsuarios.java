package interfaces;

import modelo.Usuarios;

import java.util.Date;
import java.util.List;

public interface IngresoUsuarios {
    public List AnadirUsuario(Usuarios usuarios);
    public boolean VerificarContenido (Usuarios usuarios);
    public boolean Desconectar();
    public List edad(Date fechaNacimiento);
    public List tiempoDeVinculacion(Date fechaVinculacion);
}
