package com.sistemascorporativos.miappnueva.referenciales.seguro_medico.modelos;

public class SeguroMedicoDto {
    private Integer segId;
    private String segDescripcion;

    public SeguroMedicoDto() {
    }

    public SeguroMedicoDto(Integer segId, String segDescripcion) {
        this.segId = segId;
        this.segDescripcion = segDescripcion;
    }

    public Integer getSegId() {
        return segId;
    }

    public void setSegId(Integer segId) {
        this.segId = segId;
    }

    public String getSegDescripcion() {
        return segDescripcion;
    }

    public void setSegDescripcion(String segDescripcion) {
        this.segDescripcion = segDescripcion;
    }

    @Override
    public String toString() {
        return segDescripcion;
    }
}
