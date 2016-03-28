package fox.trenton.geopath;

import com.google.gson.Gson;

/**
 * Created by trenton on 3/7/16.
 */
public class ParsePathJSON {
    private String json;

    public ParsePathJSON(String json) {
        this.json = json;
    }

    protected CustomPath parseJSON() {
        Gson gson = new Gson();

        return gson.fromJson(json, CustomPath.class);
    }
}
