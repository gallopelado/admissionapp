package com.sistemascorporativos.miappnueva.seguridad.profesional.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfesionalDto {
    private String prof_codigo_medico;
    private String profesional_descripcion;
    private String rol;
    private String prof_numero_registro;
    private Integer espec_id;
    private String especialidad;
    private String prof_activo;
    private String creacion_usuario;
    private String creacion_fecha;
    private String creacion_hora;
    private String modificacion_usuario;
    private String modificacion_fecha;
    private String modificacion_hora;
}
