package com.sistemascorporativos.miappnueva.referenciales.ciudad.modelos;

public class CiudadDto {
    private Integer ciuId;
    private String ciuDescripcion;

    public CiudadDto() {
    }

    public CiudadDto(Integer ciuId, String ciuDescripcion) {
        this.ciuId = ciuId;
        this.ciuDescripcion = ciuDescripcion;
    }

    public Integer getCiuId() {
        return ciuId;
    }

    public void setCiuId(Integer ciuId) {
        this.ciuId = ciuId;
    }

    public String getCiuDescripcion() {
        return ciuDescripcion;
    }

    public void setCiuDescripcion(String ciuDescripcion) {
        this.ciuDescripcion = ciuDescripcion;
    }

    @Override
    public String toString() {
        return ciuDescripcion;
    }
}
