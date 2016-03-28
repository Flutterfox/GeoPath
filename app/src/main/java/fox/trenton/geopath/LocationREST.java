package fox.trenton.geopath;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;
import java.util.ConcurrentModificationException;
import java.util.List;


/**
 * Created by trenton on 3/7/16.
 */
public class LocationREST {
    public static final String JSON_URL = "http://172.25.3.102:8080/GeoPathServer/rest/path/create";
    CustomPath cp;

    public CustomPath sendRequest(final List<CustomLocation> locations, Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST,
                JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showJSON(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //showResponse("There was an error.");
            }
        }) {

            public String getBodyContentType() {
                return "application/json; charset=" + getParamsEncoding();
            }

            public byte[] getBody() throws AuthFailureError {
                try {

                    String test = new GsonBuilder().create()
                            .toJson(locations);
                    return test.getBytes(getParamsEncoding());
                } catch (UnsupportedEncodingException | ConcurrentModificationException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return null;
            }

        };

        sr.setRetryPolicy(new DefaultRetryPolicy(10000, 4,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(sr);
        return cp;
    }

    public void showJSON(String json) {
/*        ParseLocJSON pj = new ParseLocJSON(json);
        pj.parseJSON();
        ll = new LocationList(ParseLocJSON.loc_id, ParseLocJSON.user_id, ParseLocJSON.type,
                ParseLocJSON.label, ParseLocJSON.description, ParseLocJSON.path_id, ParseLocJSON.lat,
                ParseLocJSON.lon, ParseLocJSON.timestamp, ParseLocJSON.position);*/

        ParsePathJSON pj = new ParsePathJSON(json);
        cp = pj.parseJSON();
    }

}
