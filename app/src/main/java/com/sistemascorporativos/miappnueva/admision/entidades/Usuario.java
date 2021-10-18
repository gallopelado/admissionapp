package com.sistemascorporativos.miappnueva.admision.entidades;

public class Usuario {
    private String codigoUsuario;
    private String passwordUsuario;
    private String nombreUsuario;
    private String apellidoUsuario;
    private String descripcionUsuario;
    private String estadoUsuario;

    public Usuario() {
    }

    public Usuario(String codigoUsuario, String passwordUsuario, String nombreUsuario, String apellidoUsuario, String descripcionUsuario, String estadoUsuario) {
        this.codigoUsuario = codigoUsuario;
        this.passwordUsuario = passwordUsuario;
        this.nombreUsuario = nombreUsuario;
        this.apellidoUsuario = apellidoUsuario;
        this.descripcionUsuario = descripcionUsuario;
        this.estadoUsuario = estadoUsuario;
    }

    public String getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(String codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public String getPasswordUsuario() {
        return passwordUsuario;
    }

    public void setPasswordUsuario(String passwordUsuario) {
        this.passwordUsuario = passwordUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidoUsuario() {
        return apellidoUsuario;
    }

    public void setApellidoUsuario(String apellidoUsuario) {
        this.apellidoUsuario = apellidoUsuario;
    }

    public String getDescripcionUsuario() {
        return descripcionUsuario;
    }

    public void setDescripcionUsuario(String descripcionUsuario) {
        this.descripcionUsuario = descripcionUsuario;
    }

    public String getEstadoUsuario() {
        return estadoUsuario;
    }

    public void setEstadoUsuario(String estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "codigoUsuario='" + codigoUsuario + '\'' +
                ", passwordUsuario='" + passwordUsuario + '\'' +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", apellidoUsuario='" + apellidoUsuario + '\'' +
                ", descripcionUsuario='" + descripcionUsuario + '\'' +
                ", estadoUsuario='" + estadoUsuario + '\'' +
                '}';
    }
}
