package com.sistemascorporativos.miappnueva.admision.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.sistemascorporativos.miappnueva.admision.entidades.Especialidad;
import com.sistemascorporativos.miappnueva.admision.entidades.PacienteDto;
import com.sistemascorporativos.miappnueva.admision.entidades.Profesional;
import com.sistemascorporativos.miappnueva.admision.entidades.Usuario;
import com.sistemascorporativos.miappnueva.db.ConexionDb;

import java.util.ArrayList;

public class AdmisionDao extends ConexionDb {

    private Context context;

    public AdmisionDao(@Nullable Context context) {
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

}
