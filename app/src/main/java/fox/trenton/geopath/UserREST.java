package fox.trenton.geopath;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


/**
 * Created by trenton on 3/7/16.
 */
public class UserREST {
    public static final String JSON_URL = "http://192.168.1.123:8080/GeoPathServer/rest/user/";
    LocationList ll;
    String response;

    public String sendRequest(String id, Context context){
        StringRequest stringRequest = new StringRequest(JSON_URL + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                         showResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(view.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        return response;
    }

    public void showResponse(String response){
        this.response = response;
    }
}
