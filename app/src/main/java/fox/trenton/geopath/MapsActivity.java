package fox.trenton.geopath;

import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    List<CustomLocation> locList;
    Timer t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        locList = new ArrayList<>();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabStop);
        fab.getDrawable().mutate().setTint(ContextCompat.getColor(this, R.color.colorIcon));
        fab.getDrawable().setAlpha(255);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t.cancel();
                goBack(view);
            }
        });
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

        //Move to current location
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        showCurrentLocation(getLocation());
        //mMap.setMyLocationEnabled(true);

        //Collects location periodically
        t = new Timer(true);
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Location loc = getLastBestLocation(locationManager);
                        if (loc != null) {
                            addCurrentLocation(loc);
                        }
                    }
                });

            }

        }, 15000, 15000);
    }

    private void showCurrentLocation(Location location) {
        LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(loc).icon(BitmapDescriptorFactory.fromResource(R.mipmap.point)));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 18));
        addToList(location);
    }

    private void addCurrentLocation(Location location) {
        LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(loc).icon(BitmapDescriptorFactory.fromResource(R.mipmap.point)));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 18));
        addToList(location);
    }



    private Location getLocation() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        String provider = "";
        if (locationManager != null) {
            provider = locationManager.getBestProvider(criteria, true);
        }

        LocationListener locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                showCurrentLocation(location);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
            }

            @Override
            public void onProviderEnabled(String s) {
            }

            @Override
            public void onProviderDisabled(String s) {
            }
        };

        Location location = null;
        if (locationManager != null) {
            //30000 is 30 seconds minimum time, 10.0f is 10 feet minimum
            locationManager.requestLocationUpdates(provider, 3000, 00.0f, locationListener);
            location = getLastBestLocation(locationManager);
        }

        return location;
    }

    //Compares gps and network locations, chooses best, stores it in the list
    private Location getLastBestLocation(LocationManager lm) {
        Location locationGPS = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location locationNet = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        long GPSLocationTime = 0;
        if (null != locationGPS) {
            GPSLocationTime = locationGPS.getTime();
        }

        long NetLocationTime = 0;

        if (null != locationNet) {
            NetLocationTime = locationNet.getTime();
        }

        if (0 < GPSLocationTime - NetLocationTime) {
            return locationGPS;
        } else {
            return locationNet;
        }
    }

    private void addToList(Location location) {
        CustomLocation cl = new CustomLocation(location, this);
        for (CustomLocation check : locList) {
            if (check.getLocID().equals(cl.getLocID())) {
                return;
            }
        }
        locList.add(cl);
    }

    private void saveLocations() {
        DatabaseConnector dc = new DatabaseConnector(this);

        for (CustomLocation cl : locList) {
            dc.open();
            if (dc.GetOneLocation(cl.getLocID()).getCount() == 0) {
                dc.InsertLocation(cl);
            }
            dc.close();
        }
    }

    public void goBack(View view){
        CustomPath cp = new CustomPath();

        if (locList.size() > 1) {
            //Saves locations to localDB
            saveLocations();

            Toast.makeText(this, "Waiting for the path to be built.", Toast.LENGTH_LONG).show();

            //Sends locations to OracleDB
            LocationREST lr = new LocationREST();
            lr.sendRequest(locList, this);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }

}
