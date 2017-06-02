package com.example.asd.iamhere;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapFragment extends Fragment {
    MapView mapView;
    GoogleMap googleMap;
    Location currentLocation;
    Address address = null;
    Bitmap snapshot;
    public static final int MY_PERMISSIONS_REQUEST_READ_LOCATION = 99;

    public MapFragment() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_LOCATION: {
                if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    setMyLocation();
                else
                    Toast.makeText(getContext(), "Permission not granted. Can't display current location.", Toast.LENGTH_LONG).show();
                break;
            }

        }
    }

    private boolean noPermissions() {
        if ((ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED)
            return false;
        return true;

    }

    @Override
    @SuppressWarnings("MissingPermission")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = (MapView) rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                    public void onMapLoaded() {
                        googleMap.snapshot(new GoogleMap.SnapshotReadyCallback() {
                            public void onSnapshotReady(Bitmap bitmap) {
                                snapshot = bitmap;
                            }
                        });
                    }
                });
                if (noPermissions()) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET},
                            MY_PERMISSIONS_REQUEST_READ_LOCATION);
                    return;
                }
                googleMap.setMyLocationEnabled(true);
                Button refresh = (Button) rootView.findViewById(R.id.button_map_refresh);
                refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setMyLocation();
                    }
                });
                Button send = (Button) rootView.findViewById(R.id.button_map_send);
                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MessageConstructor messageConstructor = new MessageConstructor();
                        messageConstructor.setCoordinates(currentLocation).setAddress(getPrintableAddress()).setScreenshot(snapshot);
                        MessageComposerFragment messageComposerFragment = new MessageComposerFragment();
                        messageComposerFragment.setMessageConstructor(messageConstructor);
                        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.map_activity,messageComposerFragment).addToBackStack(null).commit();
                    }
                });
                setMyLocation();
            }

        });

        return rootView;
    }

    @SuppressWarnings("MissingPermission")
    private void setMyLocation() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        if (noPermissions()) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (location != null) {
            currentLocation = location;
            getAddress(location.getLatitude(), location.getLongitude());
            notifyLoc();
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))
                    .zoom(17)
                    .bearing(90)
                    .tilt(40)
                    .build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    private void getAddress(double lattitude, double longtitude) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(lattitude, longtitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses != null) {
            address = addresses.get(0);
        }
    }

    private String getPrintableAddress() {
        if (address != null) {
            String addresss = address.getAddressLine(0);
            String city = address.getLocality();
            String country = address.getCountryName();
            return "Address:" + addresss + "\n"
                    + "City: " + city + "\n"
                    + "Country: " + country;
        }
        return "";
    }

    private void notifyLoc() {
        if (isNetworkAvailable() || address != null) {
            Toast.makeText(getContext(), "Your current location is:\n" + getPrintableAddress(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Enable network and refresh in order to print information about current location.", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
