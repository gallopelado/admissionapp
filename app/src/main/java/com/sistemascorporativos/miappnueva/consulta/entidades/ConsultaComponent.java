package com.sistemascorporativos.miappnueva.consulta.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase ConsultaComponent
 *
 * Sirve para representar y tranferir datos a la interfaz gráfica.
 * Si desea guardar en la base de datos, es preferible usar una clase de tipo entidad dto
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaComponent {
    private String codigoEstablecimiento;
    private String codigoPreconsulta;
    private String motivoConsulta;
    private String historialConsulta;
    private String evolucionConsulta;
    private String operacion;//Sirve para notificar el estado correcto de la operacion a la UI
}
