package com.sistemascorporativos.miappnueva.admision.entidades;

public class Profesional {
    private Usuario usuario;
    private String numeroRegistro;
    private Especialidad especialidad;
    private String activo;

    public Profesional() {
    }

    public Profesional(Usuario usuario, String numeroRegistro, Especialidad especialidad, String activo) {
        this.usuario = usuario;
        this.numeroRegistro = numeroRegistro;
        this.especialidad = especialidad;
        this.activo = activo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Profesional{" +
                "usuario=" + usuario +
                ", numeroRegistro='" + numeroRegistro + '\'' +
                ", especialidad=" + especialidad +
                ", activo='" + activo + '\'' +
                '}';
    }
}
