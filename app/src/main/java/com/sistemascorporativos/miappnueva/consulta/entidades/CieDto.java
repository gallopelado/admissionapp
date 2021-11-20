package com.sistemascorporativos.miappnueva.consulta.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CieDto {
    private String cieId;
    private String cieCodigo;
    private String cieDescripcion;
    private String cieTipo;

}
