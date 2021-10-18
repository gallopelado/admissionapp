package com.sistemascorporativos.miappnueva.admision.entidades;

public class Especialidad {
    private Integer especialidadId;
    private String especialidadDescripcion;

    public Especialidad() {
    }

    public Especialidad(Integer especialidadId, String especialidadDescripcion) {
        this.especialidadId = especialidadId;
        this.especialidadDescripcion = especialidadDescripcion;
    }

    public Integer getEspecialidadId() {
        return especialidadId;
    }

    public void setEspecialidadId(Integer especialidadId) {
        this.especialidadId = especialidadId;
    }

    public String getEspecialidadDescripcion() {
        return especialidadDescripcion;
    }

    public void setEspecialidadDescripcion(String especialidadDescripcion) {
        this.especialidadDescripcion = especialidadDescripcion;
    }

    @Override
    public String toString() {
        return "Especialidad{" +
                "especialidadId=" + especialidadId +
                ", especialidadDescripcion='" + especialidadDescripcion + '\'' +
                '}';
    }
}
