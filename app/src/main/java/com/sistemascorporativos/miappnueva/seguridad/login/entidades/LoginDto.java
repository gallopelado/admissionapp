package com.sistemascorporativos.miappnueva.seguridad.login.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    private String usuCodigoUsuario;
    private String usuPassword;
    private String usuNombres;
    private String usuApellidos;
    private String usuDescripcion;
    private String usuEstado;
    private String usuRol;
    private String usuCreacion_usuario;
    private String usuCreacion_fecha;
    private String usuCreacion_hora;
    private String usuModificacion_usuario;
    private String usuModificacion_fecha;
    private String usuModificacion_hora;
    private String operacion;
}
