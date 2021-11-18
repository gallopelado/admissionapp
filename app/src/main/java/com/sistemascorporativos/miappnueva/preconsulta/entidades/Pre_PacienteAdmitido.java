package com.sistemascorporativos.miappnueva.preconsulta.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pre_PacienteAdmitido {
    private String cedulaPaciente;
    private String nombrePaciente;
    private String consultando;
    private String fechanac;
    private String codigo_asignacion;
    private String codigo_establecimiento;
    private String creacion_fecha;
    private Double precon_temperatura_corporal;
    private Double precon_presion_arterial;
    private Double precon_frecuencia_respiratoria;
    private Double precon_pulso;
    private Double precon_peso;
    private Double precon_talla;
    private Double precon_imc;
    private Double precon_saturacion;
    private Double precon_circunferencia_abdominal;
    private String precon_motivo_consulta;
    private String precon_creacion_usuario;
    private String precon_creacion_hora;
    private String precon_modificacion_usuario;
    private String precon_modificacion_fecha;
    private String precon_modificacion_hora;
    private String operacion;
}
