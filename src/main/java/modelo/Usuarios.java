package modelo;

import java.util.Date;

public class Usuarios {
    String nombres;
    String apellidos;
    String tipoDocumento;
    String numerodocumento;
    Date fechaNacimiento;
    Date fechaVinculación;
    String cargo;
    Double salario;

    public Usuarios() {
    }

    public Usuarios(String nombres, String apellidos, String tipoDocumento, String numerodocumento, Date fechaNacimiento, Date fechaVinculación, String cargo, Double salario) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.tipoDocumento = tipoDocumento;
        this.numerodocumento = numerodocumento;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaVinculación = fechaVinculación;
        this.cargo = cargo;
        this.salario = salario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumerodocumento() {
        return numerodocumento;
    }

    public void setNumerodocumento(String numerodocumento) {
        this.numerodocumento = numerodocumento;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Date getFechaVinculación() {
        return fechaVinculación;
    }

    public void setFechaVinculación(Date fechaVinculación) {
        this.fechaVinculación = fechaVinculación;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }
}
