package fox.trenton.geopath;

/**
 * Created by trenton on 2/24/16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseConnector {

    // Declare Variables

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
    private static final String POSITION = "POSITION";


    private SQLiteDatabase database;
    private DatabaseHelper dbOpenHelper;

    public DatabaseConnector(Context context) {
        dbOpenHelper = new DatabaseHelper(context);
    }

    // Open Database function
    public void open() throws SQLException {
        // Allow database to be in writable mode
        database = dbOpenHelper.getWritableDatabase();
    }

    // Close Database function
    public void close() {
        if (database != null)
            database.close();
    }


    //Location
    // Insert Location function
    public void InsertLocation(CustomLocation customLocation) {

        ContentValues newCon = new ContentValues();
        newCon.put(LOCATIONID, customLocation.getLocID());
        newCon.put(LATITUDE, String.valueOf(customLocation.getLat()));
        newCon.put(LONGITUDE, String.valueOf(customLocation.getLon()));
        newCon.put(USERID, customLocation.getUserID());
        newCon.put(TIMEDATE, customLocation.getTimestamp().getTime());
        newCon.put(TYPE, customLocation.getType());
        newCon.put(LOCLABEL, customLocation.getLabel());
        newCon.put(LOCDESCRIPTION, customLocation.getDescription());
        newCon.put(PATHID, customLocation.getPathID());
        newCon.put(POSITION, customLocation.getPosition());

        open();
        database.insert(TABLE_LOC, null, newCon);
        close();
    }

    // Update Location function
    public void UpdateLocation(CustomLocation customLocation) {

        ContentValues editCon = new ContentValues();
        editCon.put(LOCATIONID, customLocation.getLocID());
        editCon.put(LATITUDE, String.valueOf(customLocation.getLat()));
        editCon.put(LONGITUDE, String.valueOf(customLocation.getLon()));
        editCon.put(USERID, customLocation.getUserID());
        editCon.put(TIMEDATE, customLocation.getTimestamp().getTime());
        editCon.put(TYPE, customLocation.getType());
        editCon.put(LOCLABEL, customLocation.getLabel());
        editCon.put(LOCDESCRIPTION, customLocation.getDescription());
        editCon.put(PATHID, customLocation.getPathID());
        editCon.put(POSITION, customLocation.getPosition());

        open();
        database.update(TABLE_LOC, editCon, LOCATIONID + " = \'" + customLocation.getLocID() + "\'", null);
        close();
    }

    // Delete Location function
    public void DeleteLocation(String locID) {
        open();
        database.delete(TABLE_LOC, LOCATIONID + " = \'" + locID + "\'", null);
        close();
    }

    // Capture single data by ID
    public Cursor GetOneLocation(String locID) {
        return database.query(TABLE_LOC, null, LOCATIONID + " = \'" + locID + "\'", null, null,
                null, null);
    }

    //Return cursor of locations by pathID
    public Cursor GetPathLocations(CustomPath customPath) {
        String[] whereArgs = new String[] {customPath.getPathID().trim()};
        return database.query(TABLE_LOC, null, PATHID + " = ?", whereArgs, null,
                null, null);
    }


    //Path
    // Insert Path function
    public void InsertPath(CustomPath customPath) {
        ContentValues newCon = new ContentValues();
        newCon.put(PATHID, customPath.getPathID());
        newCon.put(USERID, customPath.getUserID());
        newCon.put(PATHLABEL, customPath.getLabel());
        newCon.put(PATHDESCRIPTION, customPath.getDescription());

        open();
        database.insert(TABLE_PATH, null, newCon);
        close();
    }

    // Update Path function
    public void UpdatePath(CustomPath customPath) {
        ContentValues editCon = new ContentValues();
        editCon.put(PATHID, customPath.getPathID());
        editCon.put(USERID, customPath.getUserID());
        editCon.put(PATHLABEL, customPath.getLabel());
        editCon.put(PATHDESCRIPTION, customPath.getDescription());

        open();
        database.update(TABLE_PATH, editCon, PATHID + "=" + customPath.getPathID(), null);
        close();
    }

    // Delete Path function
    public void DeletePath(String pathID) {
        open();
        database.delete(TABLE_PATH, PATHID + "=" + pathID, null);
        close();
    }

    // Capture single data by ID
    public Cursor GetOnePath(String pathID) {
        return database.query(TABLE_PATH, null, PATHID + "=" + pathID, null, null,
                null, null);
    }

    public Cursor GetAllPaths() {
        return database.query(TABLE_PATH, null, null, null, null,
                null, null);
    }

    //Check if empty
    public boolean CheckPathsEmpty() {
        Cursor c = database.query(TABLE_PATH, null, null, null, null, null, null);
        return c.getCount() <= 0;
    }

    //Users
    // Insert Users function
    public void InsertUser(String deviceID) {
        ContentValues newCon = new ContentValues();
        newCon.put(USERID, deviceID);

        open();
        database.insert(TABLE_USERS, null, newCon);
        close();
    }

    //Check for existing user
    public Cursor GetUser(String deviceID) {
        return database.query(TABLE_USERS, null, USERID + "=" + deviceID, null, null,
                null, null);
    }

    //Check if empty
    public boolean CheckUsersEmpty() {
        Cursor c = database.query(TABLE_USERS, null, null, null, null, null, null);
        return c.getCount() <= 0;
    }
}
