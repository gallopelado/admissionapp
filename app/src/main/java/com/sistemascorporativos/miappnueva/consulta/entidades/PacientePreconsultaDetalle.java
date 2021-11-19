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
    private String estadoPaciente;
    private String telefonoPaciente;
    private String tipodocPaciente;
    private String codigoPreconsulta;
    private String codigoAsignacion;
}
