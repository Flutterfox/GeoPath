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
public class ParsePathJSON {
    public static String[] user_id, label, description, path_id;

    public static final String JSON_ARRAY = "result";
    public static final String USER_ID = "user_id";
    public static final String LABEL = "label";
    public static final String DESCRIPTION = "description";
    public static final String PATH_ID = "path_id";

    private String json;

    public ParsePathJSON(String json) {
        this.json = json;
    }

    protected void parseJSON() {
        int count = json.split(":").length;
        count = count / 4;

        user_id = new String[count];
        label = new String[count];
        description = new String[count];
        path_id = new String[count];

        Gson gson = new Gson();
        CustomPath path = gson.fromJson(json, CustomPath.class);

        user_id[0] = path.getUserID();
        label[0] = path.getLabel();
        description[0] = path.getDescription();
        path_id[0] = path.getPathID();
    }
}
