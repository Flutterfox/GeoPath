package fox.trenton.geopath;

/**
 * Created by Trenton on 3/11/2016.
 */
public class CustomPath {
    private String userID, label, description, pathID, type;

    public CustomPath() {
        userID = "";
        label = "";
        description = "";
        pathID = "";
        type = "";
    }

    public CustomPath(String userID, String label, String description, String pathID) {
        this.userID = userID;
        this.label = label;
        this.description = description;
        this.pathID = pathID;
    }

    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
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

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
