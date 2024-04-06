package com.example.national_parks;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.national_parks.adapter.CustomInfoWindow;
import com.example.national_parks.data.AsyncResponse;
import com.example.national_parks.data.Repository;
import com.example.national_parks.model.Park;
import com.example.national_parks.model.ParkViewModel;
import com.example.national_parks.util.Util;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.national_parks.databinding.ActivityMapsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private static String ARUN = "Arunodaya";
    private ActivityMapsBinding binding;
    private ParkViewModel parkViewModel;
    private List<Park> parksList;
    private CardView cd;
    private EditText stateCode;
    private ImageButton search;
    private String code ="AZ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cd = findViewById(R.id.cardview);
        stateCode =findViewById(R.id.floating_state_value);
        search = findViewById(R.id.floating_search_button);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navi_View);
        parkViewModel = new ViewModelProvider(this).get(ParkViewModel.class);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment SelectedFragment = null;
            int id =item.getItemId();
            if(id==R.id.maps_nav_button){
                if(cd.getVisibility()==View.INVISIBLE || cd.getVisibility()==View.GONE){
                    cd.setVisibility(View.VISIBLE);
                }
                // I am Gonna Show Map View
                parksList.clear();
                mMap.clear();
                getSupportFragmentManager().beginTransaction().replace(R.id.map,mapFragment).commit();
                mapFragment.getMapAsync(this);
                return true;

            } else if(id==R.id.parks_nav_button) {

                // Gonna Show park List
                SelectedFragment = ParksFragment.newInstance();
                cd.setVisibility(View.GONE);
            }
            if(SelectedFragment !=null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.map, SelectedFragment).commit();
            }
            return true;
        });

        search.setOnClickListener(view -> {
            Util.hideSoftKeyBoard(view);
            parksList.clear();
            String statecode = stateCode.getText().toString().trim();
            if(!TextUtils.isEmpty(statecode)) {
                code = statecode;
                parkViewModel.selectCode(code);
                onMapReady(mMap);
                stateCode.setText("");
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
        mMap.setOnInfoWindowClickListener(this);
        mMap.setInfoWindowAdapter(new CustomInfoWindow(getApplicationContext()));
        parksList = new ArrayList<>();
        parksList.clear();
        // Add a marker in Sydney and move the camera


        mMap.clear();
        Repository.getParks(parks -> {
            parksList = parks;

            for (Park park : parks) {

                LatLng sydney = new LatLng(Double.parseDouble(park.getLatitude()),Double.parseDouble(park.getLongitude()));
                MarkerOptions markerOptions = new MarkerOptions().position(sydney).title(park.getFullName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)).snippet(park.getStates());

                Marker marker = mMap.addMarker(markerOptions);
                marker.setTag(park);
                //mMap.addMarker(markerOptions);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,7));
                Log.d(ARUN, "onMapReady: "+park.getFullName()) ;

            }
            parkViewModel.setSelectedParks(parksList);
            Log.d("SIZE", "onMapReady: "+parksList.size());
        },code);
    }

    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {
        cd.setVisibility(View.GONE);

        parkViewModel.selectPark((Park) marker.getTag());
        getSupportFragmentManager().beginTransaction().replace(R.id.map,DetailsFragment.newInstance()).commit();


    }
}