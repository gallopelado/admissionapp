package com.sistemascorporativos.miappnueva.preconsulta.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase PreconsultaComponent
 * Sirve para representar y tranferir datos a la interfaz gráfica.
 * Si desea guardar en la base de datos, es preferible usar una clase de tipo entidad dto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class PreconsultaComponent {
    private String nroIdentificacion;
    private String nombres;
    private String apellidos;
    private String sexo;
    private String fechaNacimiento;
    private String lugarNacimiento;
    private Integer ciuId;//id de ciudad
    private String correo;
    private Integer nacId;//id de nacionalidad
    private String telefono;
    private String direccion;
    private Integer segId;//id de seguro médico
    private Integer hijos;
    private String estado_civil;
    private Integer eduId;//id de nivel educativo
    private Integer sitlabId;//id de situación laboral
    private Double latitud;
    private Double longitud;
    private Integer medId;//id del médico
    private String operacion;//Sirve para notificar el estado correcto de la operacion a la UI
}
