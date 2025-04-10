package DAO;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import Models.Movimiento;

public class MovimientoDAO {
    private int version;
    private List<Movimiento> movimientoList;

    public MovimientoDAO() {
        version = 3;
        movimientoList = new ArrayList<>();
    }

    public boolean Registrar(Activity context, Movimiento nuevo) {
        boolean rpta = false;
        DbOpenHelper dboh = new DbOpenHelper(context, version);
        SQLiteDatabase sqldb = dboh.getWritableDatabase();

        if (sqldb != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("monto", nuevo.getMonto());
            contentValues.put("descripcion", nuevo.getDescripcion());
            contentValues.put("fecha", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(nuevo.getFecha()));
            contentValues.put("esGasto", (nuevo.isEsGasto() ? 1 : 0));
            contentValues.put("idCategoria", nuevo.getIdCategoria());

            int fila = (int)sqldb.insert("Movimientos", null, contentValues);
            rpta = fila > 0;
        }
        sqldb.close();
        dboh.close();
        return rpta;
    }

    public boolean Cargar(Activity context) throws ParseException {
        boolean rpta = false;
        DbOpenHelper dboh = new DbOpenHelper(context, version);
        SQLiteDatabase sqldb = dboh.getReadableDatabase();
        String consulta = "SELECT * FROM Movimientos";
        Cursor cursor = sqldb.rawQuery(consulta, null);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        if (cursor.moveToFirst()) {
            rpta = true;
            do {
                Movimiento enlistado = new Movimiento(
                        cursor.getDouble(1),
                        cursor.getString(2),
                        dateFormat.parse(cursor.getString(3)),
                        (cursor.getInt(4) == 1 ? true : false),
                        cursor.getInt(5)
                );
                movimientoList.add(enlistado);
            } while (cursor.moveToNext());
        }
        return rpta;
    }
}
