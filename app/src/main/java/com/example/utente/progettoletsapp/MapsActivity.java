package com.example.utente.progettoletsapp;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {

    private GoogleMap mMap;
    private NavigationView menu;
    private DrawerLayout lmenu;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;
    private Button b1,b2,b3;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    int cont = 0;

    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        lmenu = (DrawerLayout) findViewById(R.id.drawer_layout);
        menu = (NavigationView) findViewById(R.id.menuLaterale);
        NavigationView.OnNavigationItemSelectedListener ls = new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {   //Selezionato primo tasto menu laterale
                    case R.id.item1:
                        allPos();
                        break;
                    case R.id.item2:
                        likedPos();
                        break;
                    case R.id.item3:
                        inform();
                }
                return true;
            }
        };
        menu.setNavigationItemSelectedListener(ls);
        AutoCompleteTextView txt = (AutoCompleteTextView) findViewById(R.id.editText);
        txt.setThreshold(2);
        txt.setAdapter(new ArrayAdapter<String>(MapsActivity.this,android.R.layout.simple_list_item_1,SetSuggerimenti.SUGG(MapsActivity.this)));
        TextView.OnEditorActionListener ls1 = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                bSearch();
                return true;
            }
        };
        txt.setOnEditorActionListener(ls1);
        b1= (Button) findViewById(R.id.bPos);
        b2= (Button) findViewById(R.id.bSave);
        b3= (Button) findViewById(R.id.bHelp);
        View.OnLongClickListener ls2=new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                switch(v.getId())
                {
                    case R.id.bSave:
                        Toast.makeText(MapsActivity.this, "Salva luogo.", Toast.LENGTH_SHORT).show();break;
                    case R.id.bPos:
                        Toast.makeText(MapsActivity.this, "Tua posizione.", Toast.LENGTH_SHORT).show();break;
                    case R.id.bHelp:
                        Toast.makeText(MapsActivity.this, "Visualizza guida.", Toast.LENGTH_SHORT).show();break;
                }
                return true;
            }
        };
        b1.setOnLongClickListener(ls2);
        b2.setOnLongClickListener(ls2);
        b3.setOnLongClickListener(ls2);


    }

    public void buttonClicked(View v) {
        DrawerLayout lmenu = (DrawerLayout) findViewById(R.id.drawer_layout);
        switch (v.getId()) {
            //Apre il drawer_menu laterale
            case R.id.bMenu:
                lmenu.openDrawer(GravityCompat.START);
                break;
            //Premuto il tasto di ricerca
            case R.id.bSearch:
                bSearch();
                break;
            //Premuto il tasto di salvataggio
            case R.id.bSave:
                bSave();
                break;
            case R.id.bPos:
                bPos();
                break;
            case R.id.bHelp:
                bHelp();
                break;
        }

    }

    public void bSave() {   //Premuto il tasto di salvataggio e dichiarazione intent di mappa e mandata latitudine e longitudine
        Intent i1 = new Intent(MapsActivity.this, SalvaLuogoActivity.class);
        i1.putExtra("codRichiesta",1);
        i1.putExtra("latitudine", Double.toString(mLastLocation.getLatitude()));
        i1.putExtra("longitudine", Double.toString(mLastLocation.getLongitude()));
        startActivity(i1);
    }

    public void bPos() {
        LatLng latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
    }

    public void bSearch() {
        EditText txt = (EditText) findViewById(R.id.editText);
        if (txt.getText().toString().isEmpty())
            Toast.makeText(this, "Inserire una parola chiave per la ricerca!", Toast.LENGTH_SHORT).show();
        else {
            Intent i = new Intent(MapsActivity.this, RicercaActivity.class);
            i.putExtra("edRic", txt.getText().toString());
            i.putExtra("codRichiesta", 1);
            startActivity(i);
        }
    }

    public void allPos() {
        Intent i = new Intent(MapsActivity.this, RicercaActivity.class);
        i.putExtra("edRic", "");
        i.putExtra("codRichiesta", 2);
        startActivity(i);
    }
    public void likedPos() {
        Intent i = new Intent(MapsActivity.this, RicercaActivity.class);
        i.putExtra("edRic", "");
        i.putExtra("codRichiesta", 3);
        startActivity(i);
    }
    public void inform()
    {   Intent i=new Intent(MapsActivity.this,InformationActivity.class);
        startActivity(i);
    }
    public void bHelp()
    {   Intent i=new Intent(MapsActivity.this,HelpActivity.class);
        startActivity(i);
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
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED || checkLocationPermission()) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
            }

        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (cont == 0) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
            ++cont;
        }
        if (location != null) {
            mLastLocation = location;
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Permission was granted.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
            }

            // other 'case' lines to check for other permissions this app might request.
            //You can add here other case statements according to your requirement.
        }
    }
}
