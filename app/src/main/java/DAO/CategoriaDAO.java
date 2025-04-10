package DAO;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import Models.Categoria;
import Models.Usuario;

public class CategoriaDAO {
    private int version;
    private List<Categoria> categoriaList;

    public CategoriaDAO() {
        version = 2;
        categoriaList = new ArrayList<>();
    }

    public boolean Registrar(Activity context, Categoria nueva) {
        boolean rpta = false;
        DbOpenHelper dboh = new DbOpenHelper(context, version);
        SQLiteDatabase sqldb = dboh.getWritableDatabase();

        if (sqldb != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("nombre", nueva.getNombre());
            contentValues.put("descripcion", nueva.getDescripcion());
            contentValues.put("icono", nueva.getIcono());

            int fila = (int)sqldb.insert("Categorias", null, contentValues);
            rpta = fila > 0;
        }
        sqldb.close();
        dboh.close();
        return rpta;
    }

    public boolean Cargar(Activity context) {
        boolean rpta = false;
        DbOpenHelper dboh = new DbOpenHelper(context, version);
        SQLiteDatabase sqldb = dboh.getReadableDatabase();
        String consulta = "SELECT * FROM Categorias";
        Cursor cursor = sqldb.rawQuery(consulta, null);

        if (cursor.moveToFirst()) {
            rpta = true;
            do {
                Categoria enlistada = new Categoria(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                );
                categoriaList.add(enlistada);
            } while (cursor.moveToNext());
        }
        return rpta;
    }

    public List<Categoria> getCategoriaList() {
        return categoriaList;
    }
}
