package com.sistemascorporativos.miappnueva.referenciales;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.sistemascorporativos.miappnueva.db.ConexionDb;

public class ReferencialDao extends ConexionDb {
    private Context context;

    public ReferencialDao(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public ReferencialDto guardar(ReferencialDto refd, String tabla) {
        int id = 0;
        try(ConexionDb conexionDb = new ConexionDb(context); SQLiteDatabase db = conexionDb.getWritableDatabase();) {
            ContentValues values = new ContentValues();
            switch (tabla) {
                case "ciudades":
                    values.put("ciu_descripcion", refd.getDescripcion());
                    break;
                case "nacionalidades":
                    values.put("nac_descripcion", refd.getDescripcion());
                    break;
                case "nivel_educativo":
                    values.put("edu_descripcion", refd.getDescripcion());
                    break;
                case "situacion_laboral":
                    values.put("sitlab_descripcion", refd.getDescripcion());
                    break;
                case "seguro_medico":
                    values.put("seg_descripcion", refd.getDescripcion());
                    break;
                case "especialidad":
                    values.put("espec_descripcion", refd.getDescripcion());
                    break;
            }
            //Insertar
            id = (int) db.insert(tabla, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id>0 ? refd : null;
    }

    public ReferencialDto actualizar(ReferencialDto refd, String tabla) {
        int id = 0;
        String clausula_where = "";
        String[] valores = null;
        try(ConexionDb conexionDb = new ConexionDb(context); SQLiteDatabase db = conexionDb.getWritableDatabase();) {
            ContentValues values = new ContentValues();
            switch (tabla) {
                case "ciudades":
                    values.put("ciu_descripcion", refd.getDescripcion());
                    clausula_where = "ciu_id = ?";
                    valores = new String[] { refd.getId() };
                    break;
                case "nacionalidades":
                    values.put("nac_descripcion", refd.getDescripcion());
                    clausula_where = "nac_id = ?";
                    valores = new String[] { refd.getId() };
                    break;
                case "nivel_educativo":
                    values.put("edu_descripcion", refd.getDescripcion());
                    clausula_where = "edu_id = ?";
                    valores = new String[] { refd.getId() };
                    break;
                case "situacion_laboral":
                    values.put("sitlab_descripcion", refd.getDescripcion());
                    clausula_where = "sitlab_id = ?";
                    valores = new String[] { refd.getId() };
                    break;
                case "seguro_medico":
                    values.put("seg_descripcion", refd.getDescripcion());
                    clausula_where = "seg_id = ?";
                    valores = new String[] { refd.getId() };
                    break;
                case "especialidad":
                    values.put("espec_descripcion", refd.getDescripcion());
                    clausula_where = "espec_id = ?";
                    valores = new String[] { refd.getId() };
                    break;
            }
            //Update
            id = db.update(tabla, values, clausula_where, valores);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id>0 ? refd : null;
    }

    public ReferencialDto eliminar(ReferencialDto refd, String tabla) {
        int id = 0;
        String clausula_where = "";
        String[] valores = null;
        try(ConexionDb conexionDb = new ConexionDb(context); SQLiteDatabase db = conexionDb.getWritableDatabase();) {
            switch (tabla) {
                case "ciudades":
                    clausula_where = "ciu_id = ?";
                    valores = new String[] { refd.getId() };
                    break;
                case "nacionalidades":
                    clausula_where = "nac_id = ?";
                    valores = new String[] { refd.getId() };
                    break;
                case "nivel_educativo":
                    clausula_where = "edu_id = ?";
                    valores = new String[] { refd.getId() };
                    break;
                case "situacion_laboral":
                    clausula_where = "sitlab_id = ?";
                    valores = new String[] { refd.getId() };
                    break;
                case "seguro_medico":
                    clausula_where = "seg_id = ?";
                    valores = new String[] { refd.getId() };
                    break;
                case "especialidad":
                    clausula_where = "espec_id = ?";
                    valores = new String[] { refd.getId() };
                    break;
            }
            //delete
            id = db.delete(tabla, clausula_where, valores);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id>0 ? refd : null;
    }
}
