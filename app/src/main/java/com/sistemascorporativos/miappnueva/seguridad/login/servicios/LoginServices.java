package com.sistemascorporativos.miappnueva.seguridad.login.servicios;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.common.hash.Hashing;
import com.sistemascorporativos.miappnueva.seguridad.login.dao.LoginDao;
import com.sistemascorporativos.miappnueva.seguridad.login.entidades.LoginDto;

import java.nio.charset.StandardCharsets;

public class LoginServices {

    private Context ctx;
    private LoginDao dao;

    public LoginServices(Context ctx) {
        this.ctx = ctx;
        this.dao = new LoginDao(ctx);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LoginDto agregarUsuario(LoginDto login) {
        return dao.agregarUsuario(login);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LoginDto modificarPassword(LoginDto login) {
        return dao.modificarPassword(login);
    }

    public LoginDto getUsuario(String codigo_usuario) {
        return dao.getUsuario(codigo_usuario);
    }

    public LoginDto checkUserAndPassword(String user, String password) {
        String password_hash = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
        LoginDto usuario = dao.getUserAndPassword(user, password_hash);
        return usuario!=null ? usuario : null;
    }
}
