package sasidhar.azuqua.com.azuquaapitest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BALASASiDHAR on 26-May-15.
 */
public class DataBaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "apiList";

    // Table Name
    private static final String TABLE_NAME = "api";


    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PROTOCOL = "protocol";
    private static final String KEY_HOST = "host";
    private static final String KEY_PORT = "port";
    private static final String KEY_DEBUG = "debug";

    // create table query string
    private static final String CREATE_API_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+KEY_ID+" INTEGER PRIMARY KEY, "+KEY_NAME+" TEXT, "+KEY_PROTOCOL+" TEXT, "+KEY_HOST+" TEXT, "+KEY_PORT+" INTEGER, "+KEY_DEBUG+" TEXT)";

    // drop table query string
    private static final String DROP_API_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;


    // Constructor
    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_API_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_API_TABLE);

        onCreate(db);
    }

    /* CRUD Operations */

    // Adding new API
    public void addAPI(APIList api) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, api.getName());
        values.put(KEY_PROTOCOL, api.getProtocol());
        values.put(KEY_HOST, api.getHost());
        values.put(KEY_PORT, api.getPort());
        values.put(KEY_DEBUG, api.getDebugMode());
        db.insert(TABLE_NAME, null, values);
        db.close();

    }

    // Getting single API
    public APIList getAPI(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_NAME, KEY_PROTOCOL, KEY_HOST, KEY_PORT}, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
        }

        APIList api = new APIList(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), Integer.parseInt(cursor.getString(4)), cursor.getString(5));

        cursor.close();
        db.close();
        return api;
    }

    // Getting All APIs
    public List<APIList> getAllAPIs() {
        List<APIList> apiLists = new ArrayList<APIList>();

        String selectQuery = "SELECT * FROM "+TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.i("ROW COUNT",""+cursor.getCount());
        if(cursor.moveToFirst()){
            do{
                APIList api = new APIList();
                api.setID(Integer.parseInt(cursor.getString(0)));
                api.setName(cursor.getString(1));
                api.setProtocol(cursor.getString(2));
                api.setHost(cursor.getString(3));
                api.setPort(Integer.parseInt(cursor.getString(4)));
                api.setDebugMode(cursor.getString(5));
                apiLists.add(api);

            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return apiLists;
    }

    // Getting APIs Count
    public int getAPIsCount() {

        String countQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        cursor.close();
        db.close();
        return cursor.getCount();

    }

    // Updating single API
    public int updateAPI(APIList api) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, api.getName());
        values.put(KEY_PROTOCOL, api.getProtocol());
        values.put(KEY_HOST, api.getHost());
        values.put(KEY_PORT, api.getPort());
        values.put(KEY_DEBUG, api.getDebugMode());

        int status = db.update(TABLE_NAME, values, KEY_ID + " = ?", new String[] { String.valueOf(api.getID()) });

        db.close();
        return status;
    }

    // Deleting single API
    public void deleteAPI(APIList api) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, KEY_ID + " = ?", new String[] { String.valueOf(api.getID()) });
        db.close();
    }

}
