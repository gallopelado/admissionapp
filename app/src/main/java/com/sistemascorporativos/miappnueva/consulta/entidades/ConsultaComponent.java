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
    private String nroIdentificacion;
    private String nombres;
    private String apellidos;
    private String codigoPreconsulta;
    private String codigoAsignacion;
    private String fechaNacimiento;
    private String lugarNacimiento;
    private String correo;
    private String telefono;
    private String estado_civil;
    private String operacion;//Sirve para notificar el estado correcto de la operacion a la UI
}
