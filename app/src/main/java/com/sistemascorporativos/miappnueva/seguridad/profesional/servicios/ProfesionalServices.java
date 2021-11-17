package com.sistemascorporativos.miappnueva.seguridad.profesional.servicios;

import android.content.Context;

import com.sistemascorporativos.miappnueva.seguridad.profesional.dao.ProfesionalDao;
import com.sistemascorporativos.miappnueva.seguridad.profesional.entidades.ProfesionalDto;

import java.util.ArrayList;

public class ProfesionalServices {

    private Context ctx;
    private ProfesionalDao dao;

    public ProfesionalServices(Context ctx) {
        this.ctx = ctx;
        dao = new ProfesionalDao(ctx);
    }

    public ArrayList<ProfesionalDto> getProfesionales() {
        return dao.getProfesionales();
    }

    public ProfesionalDto getProfesional(String codigo_medico) { return dao.getProfesional(codigo_medico); }

    public ProfesionalDto insertarNuevo(ProfesionalDto profesional) {
        return dao.insertarNuevo(profesional);
    }

    public ProfesionalDto actualizar(ProfesionalDto profesional) {
        return dao.actualizar(profesional);
    }
}
