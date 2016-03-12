package fox.trenton.geopath;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Trenton on 3/11/2016.
 */
public class CustomLocation {

    private String locID, userID, type, label, description, pathID;
    private int lat, lon;
    private Date timestamp;

    public CustomLocation() {
        locID = "";
        userID = "";
        type = "";
        label = "";
        description = "";
        pathID = "";
        lat = -1;
        lon = -1;
        timestamp = new Date();
    }

    public CustomLocation(String locID, int lat, int lon,
                    String userID, Date timestamp, String type,
                    String label, String description, String pathID) {
        this.locID = locID;
        this.lat = lat;
        this.lon = lon;
        this.userID = userID;
        this.timestamp = timestamp;
        this.type = type;
        this.label = label;
        this.description = description;
        this.pathID = pathID;
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

    public int getLat() {
        return lat;
    }
    public void setLat(int lat) {
        this.lat = lat;
    }

    public int getLon() {
        return lon;
    }
    public void setLon(int lon) {
        this.lon = lon;
    }

    public Date getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
