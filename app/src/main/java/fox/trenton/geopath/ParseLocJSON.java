package fox.trenton.geopath;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Created by trenton on 3/7/16.
 */
public class ParseLocJSON {
    public static String[] loc_id, user_id, type, label, description, path_id;
    public static double[] lat, lon;
    public static Date[] timestamp;

    public static final String JSON_ARRAY = "result";
    public static final String USER_ID = "user_id";
    public static final String LOC_ID = "loc_id";
    public static final String TYPE = "type";
    public static final String LABEL = "label";
    public static final String DESCRIPTION = "description";
    public static final String PATH_ID = "path_id";
    public static final String LATITUDE = "lat";
    public static final String LONGITUDE = "lon";
    public static final String TIMESTAMP = "timestamp";

    private String json;

    public ParseLocJSON(String json) {
        this.json = json;
    }

    protected void parseJSON() {
        int count = json.split(":").length;
        count = count / 9;

        loc_id = new String[count];
        user_id = new String[count];
        type = new String[count];
        label = new String[count];
        description = new String[count];
        path_id = new String[count];
        lat = new double[count];
        lon = new double[count];
        timestamp = new Date[count];

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });
        Gson gson = builder.create();
        CustomLocation location = gson.fromJson(json, CustomLocation.class);

        loc_id[0] = location.getLocID();
        user_id[0] = location.getUserID();
        type[0] = location.getType();
        label[0] = location.getLabel();
        description[0] = location.getDescription();
        path_id[0] = location.getPathID();
        lat[0] = location.getLat();
        lon[0] = location.getLon();
        timestamp[0] = location.getTimestamp();
    }
}
