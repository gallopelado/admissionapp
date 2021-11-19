package com.sistemascorporativos.miappnueva.consulta.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PacienteDto {
    private String pacCodigoPaciente;
    private String pacTipoDocumento;
    private String pacNombres;
    private String pacApellidos;
    private String codigoAsignacion;
    private String codigoPreconsulta;
    private String pacCorreoElectronico;
    private Integer pacTelefono;
    private String pacDireccion;
    private String pacEstadoCivil;
    private String operacion;
}