package fox.trenton.geopath;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by trenton on 3/7/16.
 */
public class LocationREST {
    public static final String JSON_URL = "http://172.25.4.15:8080/GeoPathServer/rest/path/create";
    LocationList ll;

    public void sendRequest(final List<CustomLocation> locations, Context context){
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSON(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                String json = new Gson().toJson(locations);
                params.put("locations", json);

                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        queue.add(sr);
    }

    public void showJSON(String json){
        ParseLocJSON pj = new ParseLocJSON(json);
        pj.parseJSON();
        ll = new LocationList(ParseLocJSON.loc_id, ParseLocJSON.user_id, ParseLocJSON.type,
                ParseLocJSON.label, ParseLocJSON.description, ParseLocJSON.path_id, ParseLocJSON.lat,
                ParseLocJSON.lon, ParseLocJSON.timestamp);
    }
}
