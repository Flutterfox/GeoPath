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

import java.util.HashMap;
import java.util.Map;


/**
 * Created by trenton on 3/7/16.
 */
public class UserREST {
    public static final String JSON_URL = "http://172.25.3.102:8080/GeoPathServer/rest/user/insert";
    String response;

    public String sendRequest(final String id, Context context){
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST, JSON_URL,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    showResponse(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    showResponse("There was an error.");
                }
            }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<>();
                    params.put("userID", id);

                    return params;
                }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "text/plain");
                return headers;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(10000, 4,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(sr);
        return response;
    }

    public void showResponse(String response){
        this.response = response;
    }
}
