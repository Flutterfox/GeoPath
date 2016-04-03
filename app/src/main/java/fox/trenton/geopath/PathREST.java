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
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;


/**
 * Created by trenton on 3/7/16.
 */
public class PathREST {
    public static final String JSON_URL = "http://192.168.254.8:8080/GeoPathServer/rest/path/update";
    Context context;

    public void sendRequest(final CustomPath path, final Context context) {
        this.context = context;
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST,
                JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                savePath(response);
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
                            .toJson(path);
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

    private void savePath(String json) {
        List<CustomLocation> locList = new ArrayList<>();

        //Parses the json into location objects
        ParseLocJSON pj = new ParseLocJSON(json);
        locList.addAll(pj.parseJSON());

        //Saves the analysis server's results
        Toast.makeText(context, "Saving the calculated path", Toast.LENGTH_LONG).show();
        DatabaseConnector dc = new DatabaseConnector(context);
        dc.open();
        for (CustomLocation cl : locList) {
            dc.InsertLocation(cl);
        }
        dc.close();

        //Returns to the main activity
        Intent intent;
        intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}