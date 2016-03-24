package fox.trenton.geopath;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


/**
 * Created by trenton on 3/7/16.
 */
public class PathREST {
    public static final String JSON_URL = "http://172.25.0.76:8080/GeoPathServer/rest/path/";
    LocationList ll;

    public void sendRequest(){
        StringRequest stringRequest = new StringRequest(JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(view.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void showJSON(String json){
        ParseLocJSON pj = new ParseLocJSON(json);
        pj.parseJSON();
        ll = new LocationList(ParseLocJSON.loc_id, ParseLocJSON.user_id, ParseLocJSON.type,
                ParseLocJSON.label, ParseLocJSON.description, ParseLocJSON.path_id, ParseLocJSON.lat,
                ParseLocJSON.lon, ParseLocJSON.timestamp, ParseLocJSON.position);
    }
}
