package com.sistemascorporativos.miappnueva.consulta.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PacienteDto {
    private String pacCodigoPaciente;
    private String pacNombres;
    private String pacApellidos;
    private String codigoPreconsulta;
    private String codigoEstablecimiento;
    private Integer pacTelefono;
    private String motivoConsulta;
    private String historialConsulta;
    private String evolucionConsulta;
    private String operacion;
}
