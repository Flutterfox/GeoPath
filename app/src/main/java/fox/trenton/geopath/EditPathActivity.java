package fox.trenton.geopath;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

public class EditPathActivity extends AppCompatActivity {
CustomPath cp = new CustomPath();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_path);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Gets path from intent
        cp = new Gson().fromJson(getIntent().getStringExtra("content"), CustomPath.class);

        //Used to return toasts to this activity
        final Context context = this;

        final EditText editTextLabel = (EditText)findViewById(R.id.editTextLabel);
        final EditText editTextDescription = (EditText)findViewById(R.id.editTextDescription);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.getDrawable().mutate().setTint(ContextCompat.getColor(this, R.color.colorIcon));
        fab.getDrawable().setAlpha(255);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cp.setLabel(editTextLabel.getText().toString());
                cp.setDescription(editTextDescription.getText().toString());
                savePath();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void savePath() {
        //saves to local database
        DatabaseConnector dc = new DatabaseConnector(this);
        dc.open();
        dc.InsertPath(cp);
        dc.close();

        //sends request for saving path on oracledb
        Toast.makeText(this, "Calculating path.", Toast.LENGTH_SHORT).show();
        PathREST pathREST = new PathREST();
        pathREST.sendRequest(cp, this);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }
}
