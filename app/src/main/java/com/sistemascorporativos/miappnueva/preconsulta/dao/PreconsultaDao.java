package com.sistemascorporativos.miappnueva.preconsulta.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.sistemascorporativos.miappnueva.admision.entidades.Especialidad;
import com.sistemascorporativos.miappnueva.admision.entidades.PacienteAdmitidoDetalle;
import com.sistemascorporativos.miappnueva.admision.entidades.PacienteAsignacionDto;
import com.sistemascorporativos.miappnueva.admision.entidades.PacienteDto;
import com.sistemascorporativos.miappnueva.admision.entidades.Profesional;
import com.sistemascorporativos.miappnueva.admision.entidades.Usuario;
import com.sistemascorporativos.miappnueva.db.ConexionDb;
import com.sistemascorporativos.miappnueva.preconsulta.entidades.Pre_PacienteAdmitido;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class PreconsultaDao extends ConexionDb {
    private Context context;

    public PreconsultaDao(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public ArrayList<Profesional> getProfesionales() {
        ArrayList<Profesional> listaProfesionales = new ArrayList<>();
        String querySQL = "SELECT p.prof_codigo_medico, e.espec_id, e.espec_descripcion, u.usu_nombres, u.usu_apellidos FROM profesional p LEFT JOIN usuarios u ON u.usu_codigo_usuario = p.prof_codigo_medico LEFT JOIN especialidad e ON e.espec_id = p.espec_id WHERE p.prof_activo = 't';";
        try(
                ConexionDb conexionDb = new ConexionDb(context);
                SQLiteDatabase db = conexionDb.getWritableDatabase();
                Cursor cursorProfesional =  db.rawQuery(querySQL, null);
        ) {
            while(cursorProfesional.moveToNext()) {
                Profesional obj = new Profesional();
                Usuario usuario = new Usuario();
                usuario.setCodigoUsuario(cursorProfesional.getString(0));
                usuario.setNombreUsuario(cursorProfesional.getString(3));
                usuario.setApellidoUsuario(cursorProfesional.getString(4));
                obj.setUsuario(usuario);
                Especialidad espe = new Especialidad();
                espe.setEspecialidadId(cursorProfesional.getInt(1));
                espe.setEspecialidadDescripcion(cursorProfesional.getString(2));
                obj.setEspecialidad(espe);
                listaProfesionales.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProfesionales;
    }

    public PacienteDto guardarPaciente(PacienteDto pacienteDto) {
        try(ConexionDb conexionDb = new ConexionDb(context);SQLiteDatabase db = conexionDb.getWritableDatabase();) {
            ContentValues values = new ContentValues();
            values.put("pac_codigo_paciente", pacienteDto.getPacCodigoPaciente());
            values.put("pac_tipo_documento", "CI");
            values.put("pac_nombres", pacienteDto.getPacNombres());
            values.put("pac_apellidos", pacienteDto.getPacApellidos());
            values.put("pac_sexo", pacienteDto.getPacSexo());
            values.put("pac_fechanac", pacienteDto.getPacFechaNac());
            values.put("pac_lugar_nacimiento", pacienteDto.getPacLugarNacimiento());
            values.put("ciu_id", pacienteDto.getCiuId());
            values.put("pac_correo_electronico", pacienteDto.getPacCorreoElectronico());
            values.put("nac_id", pacienteDto.getNacId());
            values.put("pac_telefono", pacienteDto.getPacTelefono());
            values.put("pac_direccion", pacienteDto.getPacDireccion());
            values.put("seg_id", pacienteDto.getSegId());
            values.put("pac_hijos", pacienteDto.getPacHijos());
            values.put("pac_estado_civil", pacienteDto.getPacEstadoCivil());
            values.put("edu_id", pacienteDto.getEduId());
            values.put("sitlab_id", pacienteDto.getSitlabId());
            values.put("pac_latitud", pacienteDto.getPacLatitud());
            values.put("pac_longitud", pacienteDto.getPacLongitud());
            //values.put("pac_creacion_usuario", pacienteDto.getPacCreacionUsuario());
            //values.put("pac_creacion_fecha", pacienteDto.getPacCreacionFecha());
            //values.put("pac_creacion_hora", pacienteDto.getPacCreacionHora());
            //Insertar
            Long id = db.insert("pacientes", null, values);
            if(id != null) {
                pacienteDto.setOperacion("INSERT");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pacienteDto;
    }

    public PacienteDto actualizarPacienteFormularioPrincipal(PacienteDto pacienteDto) {
        try(ConexionDb conexionDb = new ConexionDb(context);SQLiteDatabase db = conexionDb.getWritableDatabase();) {
            ContentValues values = new ContentValues();
            values.put("pac_nombres", pacienteDto.getPacNombres());
            values.put("pac_apellidos", pacienteDto.getPacApellidos());
            values.put("pac_sexo", pacienteDto.getPacSexo());
            values.put("pac_fechanac", pacienteDto.getPacFechaNac());
            values.put("pac_lugar_nacimiento", pacienteDto.getPacLugarNacimiento());
            values.put("ciu_id", pacienteDto.getCiuId());
            values.put("pac_correo_electronico", pacienteDto.getPacCorreoElectronico());
            values.put("nac_id", pacienteDto.getNacId());
            values.put("pac_telefono", pacienteDto.getPacTelefono());
            values.put("pac_direccion", pacienteDto.getPacDireccion());
            //Actualizar
            Integer id = db.update("pacientes", values,"pac_codigo_paciente = ?", new String[]{ pacienteDto.getPacCodigoPaciente() });
            if(id != null) {
                pacienteDto.setOperacion("UPDATE-FORMPRINCIPAL");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pacienteDto;
    }

    public PacienteDto actualizarPacienteOtrosDatos(PacienteDto pacienteDto) {
        try(ConexionDb conexionDb = new ConexionDb(context);SQLiteDatabase db = conexionDb.getWritableDatabase();) {
            ContentValues values = new ContentValues();
            values.put("seg_id", pacienteDto.getSegId());
            values.put("pac_hijos", pacienteDto.getPacHijos());
            values.put("pac_estado_civil", pacienteDto.getPacEstadoCivil());
            values.put("edu_id", pacienteDto.getEduId());
            values.put("sitlab_id", pacienteDto.getSitlabId());
            values.put("pac_latitud", pacienteDto.getPacLatitud());
            values.put("pac_longitud", pacienteDto.getPacLongitud());
            //Actualizar
            Integer id = db.update("pacientes", values,"pac_codigo_paciente = ?", new String[]{ pacienteDto.getPacCodigoPaciente() });
            if(id != null) {
                pacienteDto.setOperacion("UPDATE-OTROSDATOS");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pacienteDto;
    }

    public PacienteDto getDatosPacienteByCodigoPaciente(String codigo_paciente) {
        PacienteDto obj = new PacienteDto();
        String querySQL = "SELECT pac_codigo_paciente, pac_tipo_documento, pac_nombres, pac_apellidos, pac_sexo, pac_fechanac, pac_lugar_nacimiento, ciu_id, pac_correo_electronico, nac_id, pac_telefono, pac_direccion, seg_id, pac_hijos, pac_estado_civil, edu_id, sitlab_id, pac_latitud, pac_longitud, pac_creacion_usuario, pac_creacion_fecha, pac_creacion_hora, pac_modificacion_usuario, pac_modificacion_fecha, pac_modificacion_hora FROM pacientes WHERE pac_codigo_paciente="+codigo_paciente+";";
        try(ConexionDb conexionDb = new ConexionDb(context);SQLiteDatabase db = conexionDb.getWritableDatabase();Cursor cursor =  db.rawQuery(querySQL, null);) {
            if(cursor.moveToFirst()) {
                obj.setPacCodigoPaciente(cursor.getString(cursor.getColumnIndex("pac_codigo_paciente")));
                obj.setPacTipoDocumento(cursor.getString(cursor.getColumnIndex("pac_tipo_documento")));
                obj.setPacNombres(cursor.getString(cursor.getColumnIndex("pac_nombres")));
                obj.setPacApellidos(cursor.getString(cursor.getColumnIndex("pac_apellidos")));
                obj.setPacSexo(cursor.getString(cursor.getColumnIndex("pac_sexo")));
                obj.setPacFechaNac(cursor.getString(cursor.getColumnIndex("pac_fechanac")));
                obj.setPacLugarNacimiento(cursor.getString(cursor.getColumnIndex("pac_lugar_nacimiento")));
                obj.setCiuId(cursor.getInt(cursor.getColumnIndex("ciu_id")));
                obj.setPacCorreoElectronico(cursor.getString(cursor.getColumnIndex("pac_correo_electronico")));
                obj.setNacId(cursor.getInt(cursor.getColumnIndex("nac_id")));
                obj.setPacTelefono(cursor.getInt(cursor.getColumnIndex("pac_telefono")));
                obj.setPacDireccion(cursor.getString(cursor.getColumnIndex("pac_direccion")));
                obj.setSegId(cursor.getInt(cursor.getColumnIndex("seg_id")));
                obj.setPacHijos(cursor.getInt(cursor.getColumnIndex("pac_hijos")));
                obj.setPacEstadoCivil(cursor.getString(cursor.getColumnIndex("pac_estado_civil")));
                obj.setEduId(cursor.getInt(cursor.getColumnIndex("edu_id")));
                obj.setSitlabId(cursor.getInt(cursor.getColumnIndex("sitlab_id")));
                obj.setPacLatitud(cursor.getDouble(cursor.getColumnIndex("pac_latitud")));
                obj.setPacLongitud(cursor.getDouble(cursor.getColumnIndex("pac_longitud")));
                obj.setOperacion("SELECT");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public Integer getCantidadPacientes() {
        String querySQL = "SELECT COUNT(*) cnt FROM paciente_asignacion";
        try(ConexionDb conexionDb = new ConexionDb(context);SQLiteDatabase db = conexionDb.getWritableDatabase();Cursor cursor =  db.rawQuery(querySQL, null);) {
            if(cursor.moveToFirst()) {
                return cursor.getInt(cursor.getColumnIndex("cnt"));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String generarPacienteAsignacion(String prefijo) {
        String querySQL = "SELECT CAST(COALESCE(MAX(rowid), 1)+1 AS TEXT)rowid, pacasi_codigo_asignacion FROM paciente_asignacion";
        String cuentanro = "";
        Integer cantidadAsignaciones = getCantidadPacientes();
        if(cantidadAsignaciones==0) return "PA00000001";
        try(ConexionDb conexionDb = new ConexionDb(context);SQLiteDatabase db = conexionDb.getWritableDatabase();Cursor cursor =  db.rawQuery(querySQL, null);) {
            String cnt = "";
            if(cursor.moveToFirst()) {
                cnt = cursor.getString(cursor.getColumnIndex("rowid"));
                for(int i=cnt.length(); i<8 ; i++) {
                    cnt = "0" + cnt;
                }
                cuentanro = prefijo + cnt;
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
        return cuentanro;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public PacienteAsignacionDto insertarPacienteAsignacion(PacienteAsignacionDto pad) {
        try(ConexionDb conexionDb = new ConexionDb(context);SQLiteDatabase db = conexionDb.getWritableDatabase();) {

            /*
             * En la practica estos datos debería de venir del servidor
             * */
            String codigo_asignacion = generarPacienteAsignacion("PA");
            String creacion_fecha = LocalDate.now().toString();//fecha actual
            String creacion_hora = LocalTime.now().toString();//hora actual

            // Iniciar transacción
            db.beginTransaction();

            // Insertar en paciente_asignacion
            ContentValues pa_values = new ContentValues();
            pa_values.put("pacasi_codigo_establecimiento", pad.getPacasiCodigoEstablecimiento());
            pa_values.put("pacasi_codigo_asignacion", codigo_asignacion);//churrísimo
            pa_values.put("pac_codigo_paciente", pad.getPacCodigoPaciente());
            pa_values.put("med_id", pad.getMedId());
            pa_values.put("supl_med_id", pad.getSuplMedId());
            pa_values.put("pacasi_estado", "A");
            pa_values.put("seg_id", pad.getSegId());
            pa_values.put("pacasi_creacion_fecha", creacion_fecha);
            pa_values.put("pacasi_creacion_hora", creacion_hora);
            Long estado_pacienteasignacion = db.insert("paciente_asignacion", null, pa_values);

            // Insertar en preconsulta
            ContentValues prec_values = new ContentValues();
            prec_values.put("precon_codigo_establecimiento", pad.getPacasiCodigoEstablecimiento());
            prec_values.put("pacasi_codigo_asignacion", codigo_asignacion);
            prec_values.put("precon_creacion_fecha", creacion_fecha);
            prec_values.put("precon_creacion_hora", creacion_hora);
            Long estado_preconsulta = db.insert("preconsulta", null, prec_values);

            // Insertar en consulta
            ContentValues con_values = new ContentValues();
            con_values.put("con_codigo_establecimiento", pad.getPacasiCodigoEstablecimiento());
            con_values.put("pacasi_codigo_asignacion", codigo_asignacion);
            con_values.put("con_creacion_fecha", creacion_fecha);
            con_values.put("con_creacion_hora", creacion_hora);
            Long estado_consulta = db.insert("consulta", null, con_values);

            // Controlar los estados
            if(estado_pacienteasignacion != null && estado_preconsulta != null && estado_consulta !=null) {
                db.setTransactionSuccessful();
                pad.setOperacion("ASIGNACION-EXITOSA");
            }

            // Finalizar transacción
            db.endTransaction();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return pad;
    }

    public ArrayList<Pre_PacienteAdmitido> getPacientesAdmitidos() {
        ArrayList<Pre_PacienteAdmitido> list = new ArrayList<>();
        String querySQL = "SELECT pre.precon_codigo_establecimiento, pre.pacasi_codigo_asignacion, p.pac_codigo_paciente, p.pac_fechanac, p.pac_nombres ||' '|| p.pac_apellidos paciente, pre.precon_creacion_fecha, pre.precon_temperatura_corporal, pre.precon_presion_arterial, pre.precon_frecuencia_respiratoria, pre.precon_pulso, pre.precon_peso, pre.precon_talla, pre.precon_imc, pre.precon_saturacion, pre.precon_circunferencia_abdominal, pre.precon_motivo_consulta, pre.precon_creacion_usuario, pre.precon_creacion_hora, pre.precon_modificacion_usuario, pre.precon_modificacion_fecha, pre.precon_modificacion_hora, usu.usu_nombres || ' ' || usu.usu_apellidos medico FROM preconsulta pre LEFT JOIN paciente_asignacion pa ON pa.pacasi_codigo_establecimiento = pre.precon_codigo_establecimiento AND pa.pacasi_codigo_asignacion = pre.pacasi_codigo_asignacion LEFT JOIN pacientes p ON p.pac_codigo_paciente = pa.pac_codigo_paciente LEFT JOIN profesional prof ON prof.prof_codigo_medico = pa.med_id LEFT JOIN usuarios usu ON usu.usu_codigo_usuario = prof_codigo_medico";
        try(ConexionDb conexionDb = new ConexionDb(context);SQLiteDatabase db = conexionDb.getWritableDatabase();Cursor cursor =  db.rawQuery(querySQL, null);) {
            while(cursor.moveToNext()) {
                Pre_PacienteAdmitido obj = new Pre_PacienteAdmitido();
                obj.setCodigo_establecimiento(cursor.getString(cursor.getColumnIndex("precon_codigo_establecimiento")));
                obj.setCodigo_asignacion(cursor.getString(cursor.getColumnIndex("pacasi_codigo_asignacion")));
                obj.setCedulaPaciente(cursor.getString(cursor.getColumnIndex("pac_codigo_paciente")));//cedula
                obj.setFechanac(cursor.getString(cursor.getColumnIndex("pac_fechanac")));
                obj.setNombrePaciente(cursor.getString(cursor.getColumnIndex("paciente")));
                obj.setConsultando("Consultando con el Dr. "+cursor.getString(cursor.getColumnIndex("medico")));
                obj.setPrecon_temperatura_corporal(cursor.getDouble(cursor.getColumnIndex("precon_temperatura_corporal")));
                obj.setPrecon_presion_arterial(cursor.getDouble(cursor.getColumnIndex("precon_presion_arterial")));
                obj.setPrecon_frecuencia_respiratoria(cursor.getDouble(cursor.getColumnIndex("precon_frecuencia_respiratoria")));
                obj.setPrecon_pulso(cursor.getDouble(cursor.getColumnIndex("precon_pulso")));
                obj.setPrecon_peso(cursor.getDouble(cursor.getColumnIndex("precon_peso")));
                obj.setPrecon_talla(cursor.getDouble(cursor.getColumnIndex("precon_talla")));
                obj.setPrecon_imc(cursor.getDouble(cursor.getColumnIndex("precon_imc")));
                obj.setPrecon_saturacion(cursor.getDouble(cursor.getColumnIndex("precon_saturacion")));
                obj.setPrecon_circunferencia_abdominal(cursor.getDouble(cursor.getColumnIndex("precon_circunferencia_abdominal")));
                obj.setPrecon_motivo_consulta(cursor.getString(cursor.getColumnIndex("precon_motivo_consulta")));
                list.add(obj);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
