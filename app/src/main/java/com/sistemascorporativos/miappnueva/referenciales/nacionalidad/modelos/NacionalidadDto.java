package com.sistemascorporativos.miappnueva.referenciales.nacionalidad.modelos;

public class NacionalidadDto {
    private Integer nacId;
    private String nacDescripcion;

    public NacionalidadDto() {
    }

    public NacionalidadDto(Integer nacId, String nacDescripcion) {
        this.nacId = nacId;
        this.nacDescripcion = nacDescripcion;
    }

    public Integer getNacId() {
        return nacId;
    }

    public void setNacId(Integer nacId) {
        this.nacId = nacId;
    }

    public String getNacDescripcion() {
        return nacDescripcion;
    }

    public void setNacDescripcion(String nacDescripcion) {
        this.nacDescripcion = nacDescripcion;
    }

    @Override
    public String toString() {
        return nacDescripcion;
    }
}
