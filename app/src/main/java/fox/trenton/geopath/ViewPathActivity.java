package fox.trenton.geopath;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ViewPathActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<CustomLocation> locList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_path);

        CustomPath cp = new Gson().fromJson(getIntent().getStringExtra("content"), CustomPath.class);
        DatabaseConnector dc = new DatabaseConnector(this);
        dc.open();
        Cursor cursor = dc.GetPathLocations(cp);

        //parses the objects in the cursor
        if (cursor != null && cursor.getCount() > 0) {
            locList = new ArrayList<>();
            while (cursor.moveToNext()) {
                CustomLocation customLocation = new CustomLocation();
                customLocation.setLocID(cursor.getString(0));
                customLocation.setLat(cursor.getDouble(1));
                customLocation.setLon(cursor.getDouble(2));
                customLocation.setUserID(cursor.getString(3));
                customLocation.setTimestamp(new Date(cursor.getLong(4)));
                customLocation.setType(cursor.getString(5));
                customLocation.setLabel(cursor.getString(6));
                customLocation.setDescription(cursor.getString(7));
                customLocation.setPathID(cursor.getString(8));
                customLocation.setPosition(cursor.getInt(9));
                locList.add(customLocation);
            }
        }
        dc.close();


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        if (locList != null) {
            drawPrimaryLinePath(mMap);

            LatLng loc = new LatLng(locList.get(0).getLat(), locList.get(0).getLon());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 18));
        }
    }

    private void drawPrimaryLinePath(GoogleMap mMap)
    {
        if ( mMap == null )
        {
            return;
        }

        if ( locList.size() < 2 )
        {
            return;
        }

        PolylineOptions options = new PolylineOptions();

        options.color( Color.parseColor("#CC0000FF") );
        options.width( 5 );
        options.visible( true );

        for ( CustomLocation locRecorded : locList )
        {
            options.add( new LatLng( locRecorded.getLat(),
                    locRecorded.getLon() ) );
        }

        mMap.addPolyline(options);

    }
}
