package com.sistemascorporativos.miappnueva.seguridad.usuario.servicios;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.sistemascorporativos.miappnueva.seguridad.login.dao.LoginDao;
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LoginDto agregarUsuario(LoginDto login) {
        LoginDao ldao = new LoginDao(ctx);
        return ldao.agregarUsuario(login);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LoginDto actualizarUsuarioNoPassword(LoginDto login) {
        return dao.actualizarUsuario(login);
    }
}
