package com.sistemascorporativos.miappnueva.preconsulta.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PacienteAsignadoDto {
    private String pacasiCodigoEstablecimiento;
    private String pacasiCodigoAsignacion;
    private String pacCodigoPaciente;
    private String pacConsultaFecha;
    private String medId;
    private String suplMedId;
    private String pacasiCodigoConsultorio;
    private String pacasiHoraConsulta;
    private Integer pacasiNumeroOrdenEspera;
    private String pacasiEstado;
    private Integer segId;
    private String pacasiCreacion_usuario;
    private String pacasiCreacion_fecha;
    private String pacasiCreacion_hora;
    private String pacasiModificacionUsuario;
    private String pacasiModificacionFecha;
    private String pacasiModificacionHora;
    private String operacion;
}
