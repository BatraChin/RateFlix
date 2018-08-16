package batracorp.rateflix;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Lupe on 6/2/2017.
 */

public class DataBase extends SQLiteOpenHelper {

    public static   final   int DATABASE_VERSION = 1;
    public static   final   String  DATABASE_NAME = "Cuentas.db";

    public static final String TABLA_CUENTAS = "nombres";
    public static final String COLUMNA_CONTRASENA = "_PASS";
    public static final String COLUMNA_NOMBRE = "nombre";

    private static final String SQL_CREAR = "create table "
            + TABLA_CUENTAS + "(" + COLUMNA_CONTRASENA
            + " integer primary key autoincrement, " + COLUMNA_NOMBRE
            + " text not null);";

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREAR);

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
    }

    public void agregar(String usuario){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMNA_NOMBRE,usuario);
        db.insert(TABLA_CUENTAS,null,values);
        db.close();
    }

    public void obtener(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String[]    projection = {COLUMNA_CONTRASENA,COLUMNA_NOMBRE};

        Cursor cursor=
                db.query(TABLA_CUENTAS,
                projection,
                "_id=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);

        if(cursor!=null)
            cursor.moveToFirst();
        System.out.println("El nombe es" + cursor.getString(1));
        db.close();
    }
}
