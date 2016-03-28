package fox.trenton.geopath;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DatabaseConnector dc = new DatabaseConnector(this);
        dc.open();
        if (dc.CheckPathsEmpty()){
            //Display blank list
            String[] blankArray = {"You do not currently have any paths."};
            ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.listitem, R.id.textview, blankArray);
            ListView listView = (ListView) findViewById(R.id.listViewPaths);
            listView.setAdapter(adapter);
        }
        dc.close();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                record(view);
            }
        });

        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        dc = new DatabaseConnector(this);
        dc.open();
        if (dc.CheckUsersEmpty()){
            dc.InsertUser(android_id);
            saveUserToOracle(android_id);
        }
        dc.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.action_TestConnection) {
            Intent intent = new Intent(this, testConnection.class);
            this.startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void record(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        this.startActivity(intent);
    }

    public void saveUserToOracle(String android_id){
        UserREST ur = new UserREST();
        String response = ur.sendRequest(android_id, this);
        if (response.equals("success")) {
            Toast.makeText(MainActivity.this, "Successfully saved new user.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this, "Failed to save new user.", Toast.LENGTH_LONG).show();
        }

    }
}
