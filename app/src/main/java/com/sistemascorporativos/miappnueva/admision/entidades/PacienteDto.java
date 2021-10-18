package com.sistemascorporativos.miappnueva.admision.entidades;

public class PacienteDto {
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

    public PacienteDto() {
    }

    public PacienteDto(String pacCodigoPaciente, String pacTipoDocumento, String pacNombres, String pacApellidos, String pacSexo, String pacFechaNac, String pacLugarNacimiento, Integer ciuId, String pacCorreoElectronico, Integer nacId, Integer pacTelefono, Integer segId, Integer pacHijos, String pacEstadoCivil, Integer eduId, Integer sitlabId, Double pacLatitud, Double pacLongitud, String pacCreacionUsuario, String pacCreacionFecha, String pacCreacionHora, String pacModificacionUsuario, String pacModificacionFecha, String pacModificacionHora) {
        this.pacCodigoPaciente = pacCodigoPaciente;
        this.pacTipoDocumento = pacTipoDocumento;
        this.pacNombres = pacNombres;
        this.pacApellidos = pacApellidos;
        this.pacSexo = pacSexo;
        this.pacFechaNac = pacFechaNac;
        this.pacLugarNacimiento = pacLugarNacimiento;
        this.ciuId = ciuId;
        this.pacCorreoElectronico = pacCorreoElectronico;
        this.nacId = nacId;
        this.pacTelefono = pacTelefono;
        this.segId = segId;
        this.pacHijos = pacHijos;
        this.pacEstadoCivil = pacEstadoCivil;
        this.eduId = eduId;
        this.sitlabId = sitlabId;
        this.pacLatitud = pacLatitud;
        this.pacLongitud = pacLongitud;
        this.pacCreacionUsuario = pacCreacionUsuario;
        this.pacCreacionFecha = pacCreacionFecha;
        this.pacCreacionHora = pacCreacionHora;
        this.pacModificacionUsuario = pacModificacionUsuario;
        this.pacModificacionFecha = pacModificacionFecha;
        this.pacModificacionHora = pacModificacionHora;
    }

    public String getPacCodigoPaciente() {
        return pacCodigoPaciente;
    }

    public void setPacCodigoPaciente(String pacCodigoPaciente) {
        this.pacCodigoPaciente = pacCodigoPaciente;
    }

    public String getPacTipoDocumento() {
        return pacTipoDocumento;
    }

    public void setPacTipoDocumento(String pacTipoDocumento) {
        this.pacTipoDocumento = pacTipoDocumento;
    }

    public String getPacNombres() {
        return pacNombres;
    }

    public void setPacNombres(String pacNombres) {
        this.pacNombres = pacNombres;
    }

    public String getPacApellidos() {
        return pacApellidos;
    }

    public void setPacApellidos(String pacApellidos) {
        this.pacApellidos = pacApellidos;
    }

    public String getPacSexo() {
        return pacSexo;
    }

    public void setPacSexo(String pacSexo) {
        this.pacSexo = pacSexo;
    }

    public String getPacFechaNac() {
        return pacFechaNac;
    }

    public void setPacFechaNac(String pacFechaNac) {
        this.pacFechaNac = pacFechaNac;
    }

    public String getPacLugarNacimiento() {
        return pacLugarNacimiento;
    }

    public void setPacLugarNacimiento(String pacLugarNacimiento) {
        this.pacLugarNacimiento = pacLugarNacimiento;
    }

    public Integer getCiuId() {
        return ciuId;
    }

    public void setCiuId(Integer ciuId) {
        this.ciuId = ciuId;
    }

    public String getPacCorreoElectronico() {
        return pacCorreoElectronico;
    }

    public void setPacCorreoElectronico(String pacCorreoElectronico) {
        this.pacCorreoElectronico = pacCorreoElectronico;
    }

    public Integer getNacId() {
        return nacId;
    }

    public void setNacId(Integer nacId) {
        this.nacId = nacId;
    }

    public Integer getPacTelefono() {
        return pacTelefono;
    }

    public void setPacTelefono(Integer pacTelefono) {
        this.pacTelefono = pacTelefono;
    }

    public Integer getSegId() {
        return segId;
    }

    public void setSegId(Integer segId) {
        this.segId = segId;
    }

    public Integer getPacHijos() {
        return pacHijos;
    }

    public void setPacHijos(Integer pacHijos) {
        this.pacHijos = pacHijos;
    }

    public String getPacEstadoCivil() {
        return pacEstadoCivil;
    }

    public void setPacEstadoCivil(String pacEstadoCivil) {
        this.pacEstadoCivil = pacEstadoCivil;
    }

    public Integer getEduId() {
        return eduId;
    }

    public void setEduId(Integer eduId) {
        this.eduId = eduId;
    }

    public Integer getSitlabId() {
        return sitlabId;
    }

    public void setSitlabId(Integer sitlabId) {
        this.sitlabId = sitlabId;
    }

    public Double getPacLatitud() {
        return pacLatitud;
    }

    public void setPacLatitud(Double pacLatitud) {
        this.pacLatitud = pacLatitud;
    }

    public Double getPacLongitud() {
        return pacLongitud;
    }

    public void setPacLongitud(Double pacLongitud) {
        this.pacLongitud = pacLongitud;
    }

    public String getPacCreacionUsuario() {
        return pacCreacionUsuario;
    }

    public void setPacCreacionUsuario(String pacCreacionUsuario) {
        this.pacCreacionUsuario = pacCreacionUsuario;
    }

    public String getPacCreacionFecha() {
        return pacCreacionFecha;
    }

    public void setPacCreacionFecha(String pacCreacionFecha) {
        this.pacCreacionFecha = pacCreacionFecha;
    }

    public String getPacCreacionHora() {
        return pacCreacionHora;
    }

    public void setPacCreacionHora(String pacCreacionHora) {
        this.pacCreacionHora = pacCreacionHora;
    }

    public String getPacModificacionUsuario() {
        return pacModificacionUsuario;
    }

    public void setPacModificacionUsuario(String pacModificacionUsuario) {
        this.pacModificacionUsuario = pacModificacionUsuario;
    }

    public String getPacModificacionFecha() {
        return pacModificacionFecha;
    }

    public void setPacModificacionFecha(String pacModificacionFecha) {
        this.pacModificacionFecha = pacModificacionFecha;
    }

    public String getPacModificacionHora() {
        return pacModificacionHora;
    }

    public void setPacModificacionHora(String pacModificacionHora) {
        this.pacModificacionHora = pacModificacionHora;
    }

    @Override
    public String toString() {
        return "PacienteDto{" +
                "pacCodigoPaciente='" + pacCodigoPaciente + '\'' +
                ", pacTipoDocumento='" + pacTipoDocumento + '\'' +
                ", pacNombres='" + pacNombres + '\'' +
                ", pacApellidos='" + pacApellidos + '\'' +
                ", pacSexo='" + pacSexo + '\'' +
                ", pacFechaNac='" + pacFechaNac + '\'' +
                ", pacLugarNacimiento='" + pacLugarNacimiento + '\'' +
                ", ciuId=" + ciuId +
                ", pacCorreoElectronico='" + pacCorreoElectronico + '\'' +
                ", nacId=" + nacId +
                ", pacTelefono=" + pacTelefono +
                ", segId=" + segId +
                ", pacHijos=" + pacHijos +
                ", pacEstadoCivil='" + pacEstadoCivil + '\'' +
                ", eduId=" + eduId +
                ", sitlabId=" + sitlabId +
                ", pacLatitud=" + pacLatitud +
                ", pacLongitud=" + pacLongitud +
                ", pacCreacionUsuario='" + pacCreacionUsuario + '\'' +
                ", pacCreacionFecha='" + pacCreacionFecha + '\'' +
                ", pacCreacionHora='" + pacCreacionHora + '\'' +
                ", pacModificacionUsuario='" + pacModificacionUsuario + '\'' +
                ", pacModificacionFecha='" + pacModificacionFecha + '\'' +
                ", pacModificacionHora='" + pacModificacionHora + '\'' +
                '}';
    }
}
