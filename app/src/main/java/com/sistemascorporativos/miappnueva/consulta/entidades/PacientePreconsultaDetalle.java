package com.sistemascorporativos.miappnueva.consulta.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacientePreconsultaDetalle {
    private String cedulaPaciente;
    private String nombrePaciente;
    private String apellidoPaciente;
    private String telefonoPaciente;
    private String codigoPreconsulta;
    private String codigoEstablecimiento;
    private String motivoConsulta;
    private String historialConsulta;
    private String evolucionConsulta;
    private String con_creacion_usuario;
    private String con_creacion_hora;
    private String con_modificacion_usuario;
    private String con_modificacion_fecha;
    private String con_modificacion_hora;
    private String operacion;
}
