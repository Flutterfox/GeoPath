package fox.trenton.geopath;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;
import java.util.ConcurrentModificationException;
import java.util.List;


/**
 * Created by trenton on 3/7/16.
 */
public class LocationREST {
    public static final String JSON_URL = "http://172.25.2.109:8080/GeoPathServer/rest/path/create";
    Context context;

    public void sendRequest(final List<CustomLocation> locations,final Context context) {
        this.context = context;
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST,
                JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ParsePathJSON pj = new ParsePathJSON(response);
                Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                showResponse(pj.parseJSON());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
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
    }

    public void showResponse(CustomPath cp) {
        //Starts the next activity
        Intent intent;
        if (cp.getPathID().equals("")) {
            Toast.makeText(context, "Not enough data collected.", Toast.LENGTH_LONG).show();
            intent = new Intent(context, MainActivity.class);

        } else {
            intent = new Intent(context, EditPathActivity.class);
            intent.putExtra("content", new Gson().toJson(cp));
        }
        context.startActivity(intent);
    }

}
