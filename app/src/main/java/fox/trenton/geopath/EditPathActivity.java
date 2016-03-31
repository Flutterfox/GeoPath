package fox.trenton.geopath;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.gson.Gson;

import java.util.List;

public class EditPathActivity extends AppCompatActivity {
CustomPath cp = new CustomPath();
    List<CustomLocation> locList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_path);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cp = new Gson().fromJson(getIntent().getStringExtra("content"), CustomPath.class);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePath();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void savePath() {
        PathREST pathREST = new PathREST();
        locList.addAll(pathREST.sendRequest(cp, this));

        DatabaseConnector dc = new DatabaseConnector(this);
        dc.open();
        dc.InsertPath(cp);
        for (CustomLocation cl : locList) {
            dc.InsertLocation(cl);
        }
        dc.close();
    }
}
