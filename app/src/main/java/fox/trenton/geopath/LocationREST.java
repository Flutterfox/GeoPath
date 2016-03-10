package fox.trenton.geopath;

import android.app.Activity;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by trenton on 3/7/16.
 */
public class LocationREST {
    public static final String JSON_URL = R.string.JSON_IP + "GeoPathServer/rest/location";
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
        ll = new LocationList(ParseLocJSON.loc_ids, ParseLocJSON.user_ids, ParseLocJSON.types,
                ParseLocJSON.labels, ParseLocJSON.descriptions, ParseLocJSON.path_ids, ParseLocJSON.lats,
                ParseLocJSON.lons, ParseLocJSON.timestamps);
    }
}
