package com.sistemascorporativos.miappnueva.preconsulta.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PacienteAdmitidoDto {
    private String pacCodigoPaciente;
    private String pacTipoDocumento;
    private String pacNombres;
    private String pacApellidos;
    private String pacSexo;
    private String pacFechaNac;
    private String pacLugarNacimiento;
    private Integer ciuId;
    private String pacCorreoElectronico;
    private Integer nacId;
    private Integer pacTelefono;
    private String pacDireccion;
    private Integer segId;
    private Integer pacHijos;
    private String pacEstadoCivil;
    private Integer eduId;
    private Integer sitlabId;
    private Double pacLatitud;
    private Double pacLongitud;
    private String pacCreacionUsuario;
    private String pacCreacionFecha;
    private String pacCreacionHora;
    private String pacModificacionUsuario;
    private String pacModificacionFecha;
    private String pacModificacionHora;
    private String operacion;

}
