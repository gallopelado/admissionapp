package com.sistemascorporativos.miappnueva.consulta.entidades;

public class Cie {
    private Integer cieId;
    private String CieDescripcion;
    private String CieTipo;

    public Cie() {
    }

    public Cie(Integer cieId, String CieDescripcion, String CieTipo) {
        this.cieId = cieId;
        this.CieDescripcion = CieDescripcion;
        this.CieTipo = CieTipo;
    }

    public Integer getcieId() {
        return cieId;
    }

    public void setCieId(Integer cieId) {
        this.cieId = cieId;
    }

    public String getCieDescripcion() {
        return CieDescripcion;
    }

    public void setCieDescripcion(String CieDescripcion) {
        this.CieDescripcion = CieDescripcion;
    }

    public String getCieTipo() {
        return CieTipo;
    }

    public void setCieTipo(String CieTipo) {
        this.CieTipo = CieTipo;
    }

    @Override
    public String toString() {
        return "Cie{" +
                "cieId=" + cieId +
                ", CieDescripcion='" + CieDescripcion + '\'' +
                ", CieTipo='" + CieTipo + '\'' +
                '}';
    }
}
