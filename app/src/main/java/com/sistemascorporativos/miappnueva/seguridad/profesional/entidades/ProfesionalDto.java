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
    private String prof_numero_registro;
    private Integer espec_id;
    private String prof_activo;
}
