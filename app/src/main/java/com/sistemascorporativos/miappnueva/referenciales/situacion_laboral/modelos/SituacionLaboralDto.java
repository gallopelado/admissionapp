package com.sistemascorporativos.miappnueva.referenciales.situacion_laboral.modelos;

public class SituacionLaboralDto {
    private Integer sitlabId;
    private String sitlabDescripcion;

    public SituacionLaboralDto() {
    }

    public SituacionLaboralDto(Integer sitlabId, String sitlabDescripcion) {
        this.sitlabId = sitlabId;
        this.sitlabDescripcion = sitlabDescripcion;
    }

    public Integer getSitlabId() {
        return sitlabId;
    }

    public void setSitlabId(Integer sitlabId) {
        this.sitlabId = sitlabId;
    }

    public String getSitlabDescripcion() {
        return sitlabDescripcion;
    }

    public void setSitlabDescripcion(String sitlabDescripcion) {
        this.sitlabDescripcion = sitlabDescripcion;
    }

    @Override
    public String toString() {
        return sitlabDescripcion;
    }
}
