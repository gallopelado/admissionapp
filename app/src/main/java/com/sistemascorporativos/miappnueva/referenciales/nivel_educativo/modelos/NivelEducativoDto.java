package com.sistemascorporativos.miappnueva.referenciales.nivel_educativo.modelos;

public class NivelEducativoDto {
    private Integer eduId;
    private String eduDescripcion;

    public NivelEducativoDto() {
    }

    public NivelEducativoDto(Integer eduId, String eduDescripcion) {
        this.eduId = eduId;
        this.eduDescripcion = eduDescripcion;
    }

    public Integer getEduId() {
        return eduId;
    }

    public void setEduId(Integer eduId) {
        this.eduId = eduId;
    }

    public String getEduDescripcion() {
        return eduDescripcion;
    }

    public void setEduDescripcion(String eduDescripcion) {
        this.eduDescripcion = eduDescripcion;
    }

    @Override
    public String toString() {
        return eduDescripcion;
    }
}
