package com.sistemascorporativos.miappnueva.admision.servicios;

import android.content.Context;

import com.sistemascorporativos.miappnueva.admision.dao.AdmisionDao;
import com.sistemascorporativos.miappnueva.admision.entidades.AdmisionComponent;
import com.sistemascorporativos.miappnueva.admision.entidades.PacienteDto;
import com.sistemascorporativos.miappnueva.referenciales.ciudad.dao.CiudadDao;
import com.sistemascorporativos.miappnueva.referenciales.ciudad.modelos.CiudadDto;
import com.sistemascorporativos.miappnueva.referenciales.nacionalidad.dao.NacionalidadDao;
import com.sistemascorporativos.miappnueva.referenciales.nacionalidad.modelos.NacionalidadDto;
import com.sistemascorporativos.miappnueva.referenciales.nivel_educativo.dao.NivelEducativoDao;
import com.sistemascorporativos.miappnueva.referenciales.nivel_educativo.modelos.NivelEducativoDto;
import com.sistemascorporativos.miappnueva.referenciales.seguro_medico.dao.SeguroMedicoDao;
import com.sistemascorporativos.miappnueva.referenciales.seguro_medico.modelos.SeguroMedicoDto;
import com.sistemascorporativos.miappnueva.referenciales.situacion_laboral.dao.SituacionLaboralDao;
import com.sistemascorporativos.miappnueva.referenciales.situacion_laboral.modelos.SituacionLaboralDto;

import java.util.ArrayList;

public class AdmisionServices {
    Context ctx;

    public AdmisionServices(Context ctx) {
        this.ctx = ctx;
    }

    public ArrayList<CiudadDto> getCiudades() {
        CiudadDao ciudadDao = new CiudadDao(ctx);
        return ciudadDao.getCiudades();
    }
    public ArrayList<NacionalidadDto> getNacionalidades() {
        NacionalidadDao nacionalidadDao = new NacionalidadDao(ctx);
        return nacionalidadDao.getNacionalidades();
    }
    public ArrayList<SeguroMedicoDto> getSeguroMedico() {
        SeguroMedicoDao seguroMedicoDao = new SeguroMedicoDao(ctx);
        return seguroMedicoDao.getSeguroMedico();
    }
    public ArrayList<NivelEducativoDto> getNivelEducativo() {
        NivelEducativoDao nivelEducativoDao = new NivelEducativoDao(ctx);
        return nivelEducativoDao.getNivelEducativo();
    }
    public ArrayList<SituacionLaboralDto> getSituacionLaboral() {
        SituacionLaboralDao situacionLaboralDao = new SituacionLaboralDao(ctx);
        return situacionLaboralDao.getSituacionLaboral();
    }
    public AdmisionComponent getPacienteByCodigopaciente(String codigo_paciente){
        AdmisionComponent obj = new AdmisionComponent();
        AdmisionDao admisionDao = new AdmisionDao(ctx);
        PacienteDto paciente = admisionDao.getDatosPacienteByCodigoPaciente(codigo_paciente);
        obj.setNroIdentificacion(paciente.getPacCodigoPaciente());
        obj.setNombres(paciente.getPacNombres());
        obj.setApellidos(paciente.getPacApellidos());
        obj.setSexo(paciente.getPacSexo());
        obj.setFechaNacimiento(paciente.getPacFechaNac());
        obj.setLugarNacimiento(paciente.getPacLugarNacimiento());
        obj.setCiuId(paciente.getCiuId());
        obj.setCorreo(paciente.getPacCorreoElectronico());
        obj.setNacId(paciente.getNacId());
        if(paciente.getPacTelefono()!=null) {
            obj.setTelefono(paciente.getPacTelefono().toString());
        }
        obj.setDireccion(paciente.getPacDireccion());
        obj.setSegId(paciente.getSegId());
        obj.setHijos(paciente.getPacHijos());
        obj.setEstado_civil(paciente.getPacEstadoCivil());
        obj.setEduId(paciente.getEduId());
        obj.setSitlabId(paciente.getSitlabId());
        obj.setLatitud(paciente.getPacLatitud());
        obj.setLongitud(paciente.getPacLongitud());
        obj.setOperacion(paciente.getOperacion());
        //analizar si traemos el codigo del medico, de momento no considero necesario para admision
        return obj;
    }
    public AdmisionComponent guardarPaciente(AdmisionComponent paciente) {
        AdmisionDao admisionDao = new AdmisionDao(ctx);
        PacienteDto pacienteDto = new PacienteDto();
        pacienteDto.setPacCodigoPaciente(paciente.getNroIdentificacion());
        pacienteDto.setPacNombres(paciente.getNombres());
        pacienteDto.setPacApellidos(paciente.getApellidos());
        pacienteDto.setPacSexo(paciente.getSexo());
        pacienteDto.setPacFechaNac(paciente.getFechaNacimiento());
        pacienteDto.setPacLugarNacimiento(paciente.getLugarNacimiento());
        pacienteDto.setCiuId(paciente.getCiuId());
        pacienteDto.setPacCorreoElectronico(paciente.getCorreo());
        pacienteDto.setNacId(paciente.getNacId());
        if(paciente.getTelefono()!=null && !paciente.getTelefono().isEmpty()) {
            pacienteDto.setPacTelefono(Integer.parseInt(paciente.getTelefono()));
        }
        pacienteDto.setPacDireccion(paciente.getDireccion());
        pacienteDto.setSegId(paciente.getSegId());
        pacienteDto.setPacHijos(paciente.getHijos());
        pacienteDto.setPacEstadoCivil(paciente.getEstado_civil());
        pacienteDto.setEduId(paciente.getEduId());
        pacienteDto.setSitlabId(paciente.getSitlabId());
        pacienteDto.setPacLatitud(paciente.getLatitud());
        pacienteDto.setPacLongitud(paciente.getLongitud());

        pacienteDto = admisionDao.guardarPaciente(pacienteDto);
        if(pacienteDto.getOperacion().contains("INSERT")) {
            paciente.setOperacion("GUARDADO");
        }
        return paciente;
    }
    public AdmisionComponent actualizarPacienteOtrosDatos(AdmisionComponent paciente) {
        AdmisionDao admisionDao = new AdmisionDao(ctx);
        PacienteDto pacienteDto = new PacienteDto();
        pacienteDto.setPacCodigoPaciente(paciente.getNroIdentificacion());
        pacienteDto.setSegId(paciente.getSegId());
        pacienteDto.setPacHijos(paciente.getHijos());
        pacienteDto.setPacEstadoCivil(paciente.getEstado_civil());
        pacienteDto.setEduId(paciente.getEduId());
        pacienteDto.setSitlabId(paciente.getSitlabId());
        pacienteDto.setPacLatitud(paciente.getLatitud());
        pacienteDto.setPacLongitud(paciente.getLongitud());

        pacienteDto = admisionDao.actualizarPacienteOtrosDatos(pacienteDto);
        if(!pacienteDto.getOperacion().isEmpty()) {
            paciente.setOperacion(pacienteDto.getOperacion());
        }
        return paciente;
    }
}
