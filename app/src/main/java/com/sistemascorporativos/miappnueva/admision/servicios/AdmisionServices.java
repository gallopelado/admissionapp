package com.sistemascorporativos.miappnueva.admision.servicios;

import android.content.Context;

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
}
