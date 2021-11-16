package com.sistemascorporativos.miappnueva.preconsulta.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PacienteAmitido {
    private String cedulaPaciente;
    private String nombrePaciente;
    private String consultando;
}
