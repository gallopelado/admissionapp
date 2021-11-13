package com.sistemascorporativos.miappnueva.seguridad.usuario.servicios;

import android.content.Context;

import com.sistemascorporativos.miappnueva.seguridad.login.entidades.LoginDto;
import com.sistemascorporativos.miappnueva.seguridad.usuario.dao.UsuarioDao;

import java.util.ArrayList;

public class UsuarioServices {

    private Context ctx;
    private UsuarioDao dao;

    public UsuarioServices(Context context) {
        this.ctx = context;
        this.dao = new UsuarioDao(ctx);
    }

    public ArrayList<LoginDto> getUsuarios() {
        return dao.getUsuarios();
    }
}
