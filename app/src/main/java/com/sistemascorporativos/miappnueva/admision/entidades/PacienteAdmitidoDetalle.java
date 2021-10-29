package com.sistemascorporativos.miappnueva.admision.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacienteAdmitidoDetalle {
    private String cedulaPaciente;
    private String nombrePaciente;
    private String consultando;
}
