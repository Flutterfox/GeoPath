package fox.trenton.geopath;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class testConnection extends AppCompatActivity implements View.OnClickListener {

    public static final String JSON_URL = "http://172.25.4.125:8080/GeoPathServer/rest/location/sample";

    private Button buttonGet;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_connection);

        buttonGet = (Button) findViewById(R.id.buttonGet);
        buttonGet.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.listViewLocations);
    }

    private void sendRequest(){
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
                        Toast.makeText(testConnection.this, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String json){
        ParseLocJSON pj = new ParseLocJSON(json);
        pj.parseJSON();
        LocationListForView ll = new LocationListForView(this, ParseLocJSON.loc_id, ParseLocJSON.user_id, ParseLocJSON.type,
                ParseLocJSON.label, ParseLocJSON.description, ParseLocJSON.path_id, ParseLocJSON.lat,
                ParseLocJSON.lon, ParseLocJSON.timestamp, ParseLocJSON.position);
        listView.setAdapter(ll);
    }

    @Override
    public void onClick(View v) {
        sendRequest();
    }
}