package rode1lift.ashwin.uomtrust.mu.rod1lift.DatabaseHelper;

import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ashwin on 04-Jul-17.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final int databaseVersion = 1;
    private static final String databaseName = "rode1Lift.db";
    private static DatabaseHelper mInstance = null;
    private SQLiteDatabase db;

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createTables(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public DatabaseHelper(Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    public static DatabaseHelper getInstance(Context ctx) {

        if (mInstance == null) {
            mInstance = new DatabaseHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }

    public DatabaseHelper open() throws SQLException {
        db = this.getWritableDatabase();
        return this;
    }

    private void createTables(SQLiteDatabase db) {
        createTableAccount(db);
        createTableRequest(db);
        createTableCar(db);
        createTableDevice(db);
        createTableManageRequest(db);
        createTableRating(db);
    }

    private void createTableAccount(SQLiteDatabase db){
        String qb = "CREATE TABLE IF NOT EXISTS account (" +
                " account_id INTEGER PRIMARY KEY NOT NULL, " +
                " phone_num INTEGER, " +
                " email TEXT UNIQUE NOT NULL, " +
                " profile_picture BLOB, " +
                " full_name TEXT NOT NULL, " +
                " facebook_id TEXT, " +
                " google_id TEXT, " +
                " account_role INTEGER NOT NULL, " +
                " account_status INTEGER NOT NULL, " +
                " date_created NUMERIC DEFAULT NULL, " +
                " date_updated NUMERIC DEFAULT NULL ); ";
        db.execSQL(qb);
    }

    private void createTableRequest(SQLiteDatabase db){
        String qb = "CREATE TABLE IF NOT EXISTS request (" +
                " request_id INTEGER PRIMARY KEY NOT NULL, " +
                " account_id INTEGER NOT NULL, " +
                " request_status INTEGER, " +
                " seat_available INTEGER, " +
                " price INTEGER, " +
                " place_from TEXT DEFAULT NULL, "+
                " place_to TEXT DEFAULT NULL, "+
                " event_date NUMERIC DEFAULT NULL, "+
                " date_updated NUMERIC DEFAULT NULL, "+
                " date_created NUMERIC DEFAULT NULL ); ";
        db.execSQL(qb);
    }

    private void createTableCar(SQLiteDatabase db){
        String qb = "CREATE TABLE IF NOT EXISTS car (" +
                " car_id INTEGER PRIMARY KEY NOT NULL , " +
                " account_id INTEGER NOT NULL, " +
                " make TEXT NOT NULL, " +
                " model TEXT NOT NULL, " +
                " year INTEGER, " +
                " num_of_passenger INTEGER, "+
                " picture1 BLOB, " +
                " picture2 BLOB, " +
                " picture3 BLOB, " +
                " picture4 BLOB, " +
                " hasPic1 INTEGER DEFAULT 0, " +
                " hasPic2 INTEGER DEFAULT 0, " +
                " hasPic3 INTEGER DEFAULT 0, " +
                " hasPic4 INTEGER DEFAULT 0, " +
                " plate_num TEXT); ";
        db.execSQL(qb);
    }

    private void createTableDevice(SQLiteDatabase db){
        String qb = "CREATE TABLE IF NOT EXISTS device (" +
                " device_id INTEGER PRIMARY KEY NOT NULL , " +
                " account_id INTEGER NOT NULL, " +
                " account_token text ); ";
        db.execSQL(qb);
    }

    private void createTableManageRequest(SQLiteDatabase db){
        String qb = "CREATE TABLE IF NOT EXISTS manage_request (" +
                " manage_request_id INTEGER PRIMARY KEY NOT NULL, " +
                " request_id INTEGER NOT NULL, " +
                " account_id INTEGER  NOT NULL, " +
                " seat_requested INTEGER  NOT NULL, " +
                " car_id INTEGER NOT NULL, " +
                " request_status INTEGER, " +
                " date_updated NUMERIC DEFAULT NULL, "+
                " date_created NUMERIC DEFAULT NULL ); ";
        db.execSQL(qb);
    }

    private void createTableRating(SQLiteDatabase db){
        String qb = "CREATE TABLE IF NOT EXISTS rating (" +
                " rating_id INTEGER PRIMARY KEY NOT NULL , " +
                " account_id INTEGER NOT NULL, " +
                " car_id INTEGER NOT NULL, " +
                " request_id INTEGER NOT NULL ); ";
        db.execSQL(qb);
    }

    public Cursor executeQuery(String query, String[] selectionArgs) {
        Cursor mCursor = db.rawQuery(query, selectionArgs);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
}
