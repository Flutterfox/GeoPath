package fox.trenton.geopath;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<CustomPath> pathList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ListView listView = (ListView) findViewById(R.id.listViewPaths);
        setSupportActionBar(toolbar);

        DatabaseConnector dc = new DatabaseConnector(this);
        dc.open();
        if (dc.CheckPathsEmpty()){
            //Display blank list
            String[] blankArray = {"You do not currently have any paths."};
            ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.listitem, R.id.firstLine, blankArray);
            listView.setAdapter(adapter);
        } else {
            Cursor cursor = dc.GetAllPaths();
            //parses the objects in the cursor
            if (cursor != null && cursor.getCount() > 0) {
                pathList = new ArrayList<>();
                while (cursor.moveToNext()) {
                    CustomPath cp = new CustomPath();
                    cp.setPathID(cursor.getString(0));
                    cp.setUserID(cursor.getString(1));
                    cp.setLabel(cursor.getString(2));
                    cp.setDescription(cursor.getString(3));
                    pathList.add(cp);
                }

                PathArrayAdapter adapter = new PathArrayAdapter(this, R.layout.listitem, pathList);
                listView = (ListView) findViewById(R.id.listViewPaths);
                listView.setAdapter(adapter);
            }


        }
        dc.close();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                record(view);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showPath(pathList.get(position));
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

        return super.onOptionsItemSelected(item);
    }

    public void record(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        this.startActivity(intent);
    }

    public void showPath(CustomPath customPath) {
        Intent intent = new Intent(this, ViewPathActivity.class);
        intent.putExtra("content", new Gson().toJson(customPath));
        this.startActivity(intent);
    }

    public void saveUserToOracle(String android_id){
        UserREST ur = new UserREST();
        ur.sendRequest(android_id, this);
    }

    private class PathArrayAdapter extends ArrayAdapter<CustomPath> {
        int resource;
        String response;
        Context context;

        public PathArrayAdapter(Context context, int textViewResourceId,
                                  List<CustomPath> objects) {
            super(context, textViewResourceId, objects);
            this.resource = textViewResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LinearLayout alertView;
            //Get the current alert object
            CustomPath cp = getItem(position);

            //Inflate the view
            if(convertView==null)
            {
                alertView = new LinearLayout(getContext());
                String inflater = Context.LAYOUT_INFLATER_SERVICE;
                LayoutInflater vi;
                vi = (LayoutInflater)getContext().getSystemService(inflater);
                vi.inflate(resource, alertView, true);
            }
            else
            {
                alertView = (LinearLayout) convertView;
            }
            //Get the text boxes from the listitem.xml file
            TextView pathLabel =(TextView)alertView.findViewById(R.id.firstLine);
            TextView pathDescription =(TextView)alertView.findViewById(R.id.secondLine);

            //Assign the appropriate data from our alert object above
            pathLabel.setText(cp.getLabel());
            pathDescription.setText(cp.getDescription());

            return alertView;
        }

    }

}
