package fox.trenton.geopath;

/**
 * Created by trenton on 2/24/16.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Declare Variables
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DB_NAME = "GeoPath";

    //Table Names
    private static final String TABLE_LOC = "LOCATION";
    private static final String TABLE_PATH = "PATH";
    private static final String TABLE_USERS = "USERS";

    //Common Column names
    private static final String USERID = "USERID";
    private static final String PATHID = "PATHID";

    //Path Table - Column names
    private static final String PATHLABEL = "LABEL";
    private static final String PATHDESCRIPTION = "DESCRIPTION";

    //Location Table - Column names
    private static final String LOCATIONID = "LOCATIONID";
    private static final String LATITUDE = "LATITUDE";
    private static final String LONGITUDE = "LONGITUDE";
    private static final String TIMEDATE = "TIMEDATE";
    private static final String TYPE = "TYPE";
    private static final String LOCLABEL = "LABEL";
    private static final String LOCDESCRIPTION = "DESCRIPTION";

    public DatabaseHelper(Context context, CursorFactory factory, int version) {
        super(context, DB_NAME, factory, version);
    }

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    //Table Create Statements
    //Create Table Users
    private static final String CREATE_TABLE_USERS = "CREATE TABLE "
            + TABLE_USERS + "(" + USERID + " TEXT PRIMARY KEY)";

    //Create Table Path
    private static final String CREATE_TABLE_PATH = "CREATE TABLE " + TABLE_PATH + "("
            + PATHID + " TEXT PRIMARY KEY, "
            + USERID + " TEXT,"
            + PATHLABEL + " TEXT,"
            + PATHDESCRIPTION + " TEXT)";

    //Create Table Location
    private static final String CREATE_TABLE_LOCATION = "CREATE TABLE " + TABLE_LOC + "("
            + LOCATIONID + " TEXT PRIMARY KEY, "
            + LATITUDE + " INTEGER, "
            + LONGITUDE + " INTEGER, "
            + USERID + " TEXT, "
            + TIMEDATE + " DATETIME, "
            + TYPE + " TEXT, "
            + LOCLABEL + " TEXT, "
            + LOCDESCRIPTION + " TEXT, "
            + PATHID + " TEXT)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creating required tables
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_PATH);
        db.execSQL(CREATE_TABLE_LOCATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Database will be wiped on version change
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOC);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATH);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        //Create new tables
        onCreate(db);
    }

}
