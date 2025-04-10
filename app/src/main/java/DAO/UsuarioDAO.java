package DAO;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import Models.Usuario;

public class UsuarioDAO {
    private int version;
    private List<Usuario> usuarioList;

    public UsuarioDAO() {
        version = 1;
        usuarioList = new ArrayList<>();
    }

    public boolean Registrar(Activity context, Usuario nuevo) {
        boolean rpta = false;
        DbOpenHelper dboh = new DbOpenHelper(context, version);
        SQLiteDatabase sqldb = dboh.getWritableDatabase();

        if (sqldb != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("nombre", nuevo.getNombre());
            contentValues.put("apellido", nuevo.getApellido());
            contentValues.put("nickname", nuevo.getNickname());
            contentValues.put("password", nuevo.getPassword());
            contentValues.put("email", nuevo.getEmail());
            contentValues.put("rol", nuevo.getRol());
            contentValues.put("sincNube", (nuevo.isSincNube() ? 1 : 0));

            int fila = (int)sqldb.insert("Usuarios", null, contentValues);
            rpta = fila > 0;
        }
        sqldb.close();
        dboh.close();
        return rpta;
    }

    public Usuario Validar(String nickname, String password, Activity context) {
        DbOpenHelper dboh = new DbOpenHelper(context, version);
        SQLiteDatabase sqldb = dboh.getReadableDatabase();
        Cursor cursor = sqldb.rawQuery("SELECT nickname, password, rol FROM Usuarios WHERE nickname = ? AND password = ?", new String[] {nickname, password});

        if (cursor.moveToFirst()) {
            Usuario valido = new Usuario(
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(6)
            );
            cursor.close();
            return valido;
        }
        cursor.close();
        return null;
    }

    public boolean Cargar(Activity context) {
        boolean rpta = false;
        DbOpenHelper dboh = new DbOpenHelper(context, version);
        SQLiteDatabase sqldb = dboh.getReadableDatabase();
        String consulta = "SELECT * FROM Usuarios";
        Cursor cursor = sqldb.rawQuery(consulta, null);

        if (cursor.moveToFirst()) {
            rpta = true;
            do {
                Usuario enlistado = new Usuario(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        (cursor.getInt(7) == 1 ? true : false)
                );
                usuarioList.add(enlistado);
            } while (cursor.moveToNext());
        }
        return rpta;
    }
}
