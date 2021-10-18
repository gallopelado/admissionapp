package com.sistemascorporativos.miappnueva.admision.entidades;

public class PacienteAsignacionDto {
    private String pacasiCodigoEstablecimiento;
    private String pacasiCodigoAsignacion;
    private String pacCodigoPaciente;
    private String pacConsultaFecha;
    private Integer medId;
    private Integer suplMedId;
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

    public PacienteAsignacionDto() {
    }

    public PacienteAsignacionDto(String pacasiCodigoEstablecimiento, String pacasiCodigoAsignacion, String pacCodigoPaciente, String pacConsultaFecha, Integer medId, Integer suplMedId, String pacasiCodigoConsultorio, String pacasiHoraConsulta, Integer pacasiNumeroOrdenEspera, String pacasiEstado, Integer segId, String pacasiCreacion_usuario, String pacasiCreacion_fecha, String pacasiCreacion_hora, String pacasiModificacionUsuario, String pacasiModificacionFecha, String pacasiModificacionHora) {
        this.pacasiCodigoEstablecimiento = pacasiCodigoEstablecimiento;
        this.pacasiCodigoAsignacion = pacasiCodigoAsignacion;
        this.pacCodigoPaciente = pacCodigoPaciente;
        this.pacConsultaFecha = pacConsultaFecha;
        this.medId = medId;
        this.suplMedId = suplMedId;
        this.pacasiCodigoConsultorio = pacasiCodigoConsultorio;
        this.pacasiHoraConsulta = pacasiHoraConsulta;
        this.pacasiNumeroOrdenEspera = pacasiNumeroOrdenEspera;
        this.pacasiEstado = pacasiEstado;
        this.segId = segId;
        this.pacasiCreacion_usuario = pacasiCreacion_usuario;
        this.pacasiCreacion_fecha = pacasiCreacion_fecha;
        this.pacasiCreacion_hora = pacasiCreacion_hora;
        this.pacasiModificacionUsuario = pacasiModificacionUsuario;
        this.pacasiModificacionFecha = pacasiModificacionFecha;
        this.pacasiModificacionHora = pacasiModificacionHora;
    }

    public String getPacasiCodigoEstablecimiento() {
        return pacasiCodigoEstablecimiento;
    }

    public void setPacasiCodigoEstablecimiento(String pacasiCodigoEstablecimiento) {
        this.pacasiCodigoEstablecimiento = pacasiCodigoEstablecimiento;
    }

    public String getPacasiCodigoAsignacion() {
        return pacasiCodigoAsignacion;
    }

    public void setPacasiCodigoAsignacion(String pacasiCodigoAsignacion) {
        this.pacasiCodigoAsignacion = pacasiCodigoAsignacion;
    }

    public String getPacCodigoPaciente() {
        return pacCodigoPaciente;
    }

    public void setPacCodigoPaciente(String pacCodigoPaciente) {
        this.pacCodigoPaciente = pacCodigoPaciente;
    }

    public String getPacConsultaFecha() {
        return pacConsultaFecha;
    }

    public void setPacConsultaFecha(String pacConsultaFecha) {
        this.pacConsultaFecha = pacConsultaFecha;
    }

    public Integer getMedId() {
        return medId;
    }

    public void setMedId(Integer medId) {
        this.medId = medId;
    }

    public Integer getSuplMedId() {
        return suplMedId;
    }

    public void setSuplMedId(Integer suplMedId) {
        this.suplMedId = suplMedId;
    }

    public String getPacasiCodigoConsultorio() {
        return pacasiCodigoConsultorio;
    }

    public void setPacasiCodigoConsultorio(String pacasiCodigoConsultorio) {
        this.pacasiCodigoConsultorio = pacasiCodigoConsultorio;
    }

    public String getPacasiHoraConsulta() {
        return pacasiHoraConsulta;
    }

    public void setPacasiHoraConsulta(String pacasiHoraConsulta) {
        this.pacasiHoraConsulta = pacasiHoraConsulta;
    }

    public Integer getPacasiNumeroOrdenEspera() {
        return pacasiNumeroOrdenEspera;
    }

    public void setPacasiNumeroOrdenEspera(Integer pacasiNumeroOrdenEspera) {
        this.pacasiNumeroOrdenEspera = pacasiNumeroOrdenEspera;
    }

    public String getPacasiEstado() {
        return pacasiEstado;
    }

    public void setPacasiEstado(String pacasiEstado) {
        this.pacasiEstado = pacasiEstado;
    }

    public Integer getSegId() {
        return segId;
    }

    public void setSegId(Integer segId) {
        this.segId = segId;
    }

    public String getPacasiCreacion_usuario() {
        return pacasiCreacion_usuario;
    }

    public void setPacasiCreacion_usuario(String pacasiCreacion_usuario) {
        this.pacasiCreacion_usuario = pacasiCreacion_usuario;
    }

    public String getPacasiCreacion_fecha() {
        return pacasiCreacion_fecha;
    }

    public void setPacasiCreacion_fecha(String pacasiCreacion_fecha) {
        this.pacasiCreacion_fecha = pacasiCreacion_fecha;
    }

    public String getPacasiCreacion_hora() {
        return pacasiCreacion_hora;
    }

    public void setPacasiCreacion_hora(String pacasiCreacion_hora) {
        this.pacasiCreacion_hora = pacasiCreacion_hora;
    }

    public String getPacasiModificacionUsuario() {
        return pacasiModificacionUsuario;
    }

    public void setPacasiModificacionUsuario(String pacasiModificacionUsuario) {
        this.pacasiModificacionUsuario = pacasiModificacionUsuario;
    }

    public String getPacasiModificacionFecha() {
        return pacasiModificacionFecha;
    }

    public void setPacasiModificacionFecha(String pacasiModificacionFecha) {
        this.pacasiModificacionFecha = pacasiModificacionFecha;
    }

    public String getPacasiModificacionHora() {
        return pacasiModificacionHora;
    }

    public void setPacasiModificacionHora(String pacasiModificacionHora) {
        this.pacasiModificacionHora = pacasiModificacionHora;
    }

    @Override
    public String toString() {
        return "PacienteAsignacionDto{" +
                "pacasiCodigoEstablecimiento='" + pacasiCodigoEstablecimiento + '\'' +
                ", pacasiCodigoAsignacion='" + pacasiCodigoAsignacion + '\'' +
                ", pacCodigoPaciente='" + pacCodigoPaciente + '\'' +
                ", pacConsultaFecha='" + pacConsultaFecha + '\'' +
                ", medId=" + medId +
                ", suplMedId=" + suplMedId +
                ", pacasiCodigoConsultorio='" + pacasiCodigoConsultorio + '\'' +
                ", pacasiHoraConsulta='" + pacasiHoraConsulta + '\'' +
                ", pacasiNumeroOrdenEspera=" + pacasiNumeroOrdenEspera +
                ", pacasiEstado='" + pacasiEstado + '\'' +
                ", segId=" + segId +
                ", pacasiCreacion_usuario='" + pacasiCreacion_usuario + '\'' +
                ", pacasiCreacion_fecha='" + pacasiCreacion_fecha + '\'' +
                ", pacasiCreacion_hora='" + pacasiCreacion_hora + '\'' +
                ", pacasiModificacionUsuario='" + pacasiModificacionUsuario + '\'' +
                ", pacasiModificacionFecha='" + pacasiModificacionFecha + '\'' +
                ", pacasiModificacionHora='" + pacasiModificacionHora + '\'' +
                '}';
    }
}
