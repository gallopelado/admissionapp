package com.sistemascorporativos.miappnueva.consulta.servicios;

import android.content.Context;

import com.sistemascorporativos.miappnueva.consulta.dao.ConsultaDao;
import com.sistemascorporativos.miappnueva.consulta.entidades.ConsultaComponent;
import com.sistemascorporativos.miappnueva.consulta.entidades.PacienteDto;
import com.sistemascorporativos.miappnueva.consulta.entidades.PacientePreconsultaDetalle;
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

public class ConsultaServices {
    private Context ctx;

    public ConsultaServices(Context ctx) {
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
    public ConsultaComponent getPacienteByCodigopaciente(String codigo_paciente){
        ConsultaComponent obj = new ConsultaComponent();
        ConsultaDao consultaDao = new ConsultaDao(ctx);
        PacienteDto patience = consultaDao.getDatosPacienteByCodigoPaciente(codigo_paciente);
        obj.setNroIdentificacion(patience.getPacCodigoPaciente());
        obj.setNombres(patience.getPacNombres());
        obj.setApellidos(patience.getPacApellidos());
        obj.setCorreo(patience.getPacCorreoElectronico());
        if(patience.getPacTelefono()!=null) {
            obj.setTelefono(patience.getPacTelefono().toString());
        }
        obj.setEstado_civil(patience.getPacEstadoCivil());
        obj.setOperacion(patience.getOperacion());
        return obj;
    }

    public ArrayList<PacientePreconsultaDetalle> getPacientesPreconsulta() {
        ConsultaDao consultaDao = new ConsultaDao(ctx);
        return consultaDao.getPacientesPreconsulta();
    }
}
