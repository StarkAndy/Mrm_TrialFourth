package com.example.ishu.repairproject;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

public class LocationDisplayer extends Fragment implements OnMapReadyCallback{

    private GoogleMap mMap;
    ImageView img;
    EditText search;
    Double lat=0.0,longi=0.0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_location_displayer, container, false);

        img=rootView.findViewById(R.id.imgsearch);
        search=rootView.findViewById(R.id.search);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                search_data.search1=search.getText().toString().trim().toUpperCase();
                Intent in = new Intent(getActivity(),testing.class);
                startActivity(in);
//                testing m= (testing) getActivity();
//                m.f2();
                //m.f1(search.getText().toString());
            }
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getLocationInUi();
        return rootView;
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
        String address="";
        String city="";

        Double[] lat1={12.9066,12.9055,lat};
        Double[] long1={77.623,77.625,longi};

        for(int i=0;i<lat1.length;i++) {

            // Add a marker in Sydney and move the camera
            LatLng sydney = new LatLng(lat1[i], long1[i]);

//            List<Address> addresses = null;
//            try {
//            /*
//             * Return 1 address.
//             */
//                Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
//                addresses = geocoder.getFromLocation(lat1[i], long1[i], 1);
//                address = addresses.get(0).getAddressLine(0);
//                city = addresses.get(0).getLocality();
//
//            } catch (Exception e1) {
//                Toast.makeText(getActivity(), e1.getMessage(), Toast.LENGTH_SHORT).show();
//            }
            //Toast.makeText(getActivity(), lat+" "+longi, Toast.LENGTH_SHORT).show();
            mMap.addMarker(new MarkerOptions().position(sydney).title(city+" "+address).icon(BitmapDescriptorFactory.fromResource(R.drawable.image1)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(sydney, 13);
            mMap.animateCamera(yourLocation);
        }
    }

    public void getLocationInUi() {
        //will check permission
        permissionCheckFirstTime();
        //Toast.makeText(getActivity(), "inside getLocationInUi", Toast.LENGTH_SHORT).show();
        if (permissionCheckFirstTime()) {
            //Toast.makeText(getActivity(), "inside getLocationInUi if ", Toast.LENGTH_SHORT).show();
            locationGetterInUi();
            //location class will give you latitude and longitude when you all those function
        }
    }

    public void locationGetterInUi(){

       // Toast.makeText(getActivity(), "inside locationGetterInUi", Toast.LENGTH_SHORT).show();
        LocationGetter locationGetter=new LocationGetter(getActivity());
        Location location = locationGetter.getLocation();
        try {
            if (location != null) {
                //Toast.makeText(getActivity(), "inside locationGetterInUi if ", Toast.LENGTH_SHORT).show();
                //IN parameter of context the activity where you want to get the location
                lat=locationGetter.getLatitude();
                longi=locationGetter.getLongitude();
                Toast.makeText(getActivity(), "" + locationGetter.getLatitude() + locationGetter.getLongitude(), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean permissionCheckFirstTime() {

        boolean locationStatus = false;

        if (Build.VERSION.SDK_INT < 23) {


            //here in parameter of context (addCompany.this) we have to give the activity where we have to get the location
            if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.


                //Requesting permission
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);


            } else {
                //Setting the location status as true if the permission is already granted
                locationStatus = true;

                //initialinzing the location Getter class if the permission is already granted

            }
        } else {
            locationStatus=true;
        }
        //Toast.makeText(getActivity(), "inside permissionCheckFirstTime "+locationStatus, Toast.LENGTH_SHORT).show();
        return locationStatus;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_LONG).show();
            locationGetterInUi();



            //We can call the get location if the permission is succesfully done for the immediate response


        }
    }

}
