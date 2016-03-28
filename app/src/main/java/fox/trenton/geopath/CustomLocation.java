package fox.trenton.geopath;

import android.content.Context;
import android.location.Location;
import android.provider.Settings;

import java.util.Date;

/**
 * Created by Trenton on 3/11/2016.
 */
public class CustomLocation {

    private String locID, userID, type, label, description, pathID;
    private Double lat, lon;
    private Date timestamp;
    private int position;

    public CustomLocation() {
        locID = "";
        userID = "";
        type = "";
        label = "";
        description = "";
        pathID = "";
        lat = -1D;
        lon = -1D;
        timestamp = new Date();
        position = -1;
    }

    public CustomLocation(String locID, Double lat, Double lon,
                               String userID, Date timestamp, String type,
                               String label, String description, String pathID, int position) {
        this.locID = locID;
        this.lat = lat;
        this.lon = lon;
        this.userID = userID;
        this.timestamp = timestamp;
        this.type = type;
        this.label = label;
        this.description = description;
        this.pathID = pathID;
        this.position = position;
    }

    public CustomLocation(Location location, Context context) {
        String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        Date time = new Date(location.getTime());
        this.locID = time.getTime() + android_id;
        this.lat = location.getLatitude();
        this.lon = location.getLongitude();
        this.userID = android_id;
        this.timestamp = time;
        this.type = "general";
        this.label = "";
        this.description = "";
        this.pathID = "";
        this.position = -1;
    }

    public String getLocID() {
        return locID;
    }
    public void setLocID(String locID) {
        this.locID = locID;
    }

    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getPathID() {
        return pathID;
    }
    public void setPathID(String pathID) {
        this.pathID = pathID;
    }

    public Double getLat() {
        return lat;
    }
    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }
    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Date getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getPosition() { return  position;}
    public void setPosition(int position) { this.position = position; }
}
