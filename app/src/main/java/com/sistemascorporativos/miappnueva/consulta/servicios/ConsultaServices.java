package com.sistemascorporativos.miappnueva.consulta.servicios;

import android.content.Context;

import com.sistemascorporativos.miappnueva.consulta.dao.ConsultaDao;
import com.sistemascorporativos.miappnueva.consulta.entidades.ConsultaComponent;
import com.sistemascorporativos.miappnueva.consulta.entidades.PacienteDto;
import com.sistemascorporativos.miappnueva.consulta.entidades.PacientePreconsultaDetalle;


import java.util.ArrayList;

public class ConsultaServices {
    private Context ctx;

    public ConsultaServices(Context ctx) {
        this.ctx = ctx;
    }

    public ConsultaComponent getPacienteByCodigopaciente(String codigo_paciente){
        ConsultaComponent obj = new ConsultaComponent();
        ConsultaDao consultaDao = new ConsultaDao(ctx);
        PacienteDto patience = consultaDao.getDatosPacienteByCodigoPaciente(codigo_paciente);
        obj.setMotivoConsulta(patience.getMotivoConsulta());
        obj.setHistorialConsulta(patience.getHistorialConsulta());
        obj.setEvolucionConsulta(patience.getEvolucionConsulta());
        obj.setOperacion(patience.getOperacion());
        return obj;
    }

    public ConsultaComponent actualizarPacienteOtrosDatos(ConsultaComponent paciente) {
        ConsultaDao consultaDao = new ConsultaDao(ctx);
        PacienteDto pacienteDto = new PacienteDto();
        pacienteDto.setCodigoPreconsulta(paciente.getCodigoPreconsulta());
        pacienteDto.setCodigoEstablecimiento(paciente.getCodigoEstablecimiento());
        pacienteDto.setMotivoConsulta(paciente.getMotivoConsulta());
        pacienteDto.setHistorialConsulta(paciente.getHistorialConsulta());
        pacienteDto.setEvolucionConsulta(paciente.getEvolucionConsulta());
        pacienteDto = consultaDao.actualizarPacienteOtrosDatos(pacienteDto);
        if(!pacienteDto.getOperacion().isEmpty()) {
            paciente.setOperacion(pacienteDto.getOperacion());
        }
        return paciente;
    }
}
