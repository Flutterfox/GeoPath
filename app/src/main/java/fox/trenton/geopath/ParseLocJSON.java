package fox.trenton.geopath;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by trenton on 3/7/16.
 */
public class ParseLocJSON {
    public static String[] loc_ids, user_ids, types, labels, descriptions, path_ids;
    public static int[] lats, lons;
    public static Date[] timestamps;

    public static final String JSON_ARRAY = "result";
    public static final String USER_ID = "user_ids";
    public static final String LOC_ID = "loc_ids";
    public static final String TYPE = "types";
    public static final String LABEL = "labels";
    public static final String DESCRIPTION = "descriptions";
    public static final String PATH_ID = "path_ids";
    public static final String LATITUDE = "lats";
    public static final String LONGITUDE = "lons";
    public static final String TIMESTAMP = "timestamps";

    private JSONArray locations = null;

    private String json;

    public ParseLocJSON(String json){
        this.json = json;
    }

    protected void parseJSON(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            locations = jsonObject.getJSONArray(JSON_ARRAY);

            loc_ids = new String[locations.length()];
            user_ids = new String[locations.length()];
            types = new String[locations.length()];
            labels = new String[locations.length()];
            descriptions = new String[locations.length()];
            path_ids = new String[locations.length()];
            lats = new int[locations.length()];
            lons = new int[locations.length()];
            timestamps = new Date[locations.length()];


            for(int i=0;i< locations.length();i++){
                JSONObject jo = locations.getJSONObject(i);
                loc_ids[i] = jo.getString(LOC_ID);
                user_ids[i] = jo.getString(USER_ID);
                types[i] = jo.getString(TYPE);
                labels[i] = jo.getString(LABEL);
                descriptions[i] = jo.getString(DESCRIPTION);
                path_ids[i] = jo.getString(PATH_ID);
                lats[i] = jo.getInt(LATITUDE);
                lons[i] = jo.getInt(LONGITUDE);
                timestamps[i] = new Date(jo.getString(TIMESTAMP));;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
