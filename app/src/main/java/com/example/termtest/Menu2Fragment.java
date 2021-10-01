package com.example.termtest;

import android.Manifest;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import noman.googleplaces.NRPlaces;
import noman.googleplaces.Place;
import noman.googleplaces.PlaceType;
import noman.googleplaces.PlacesException;
import noman.googleplaces.PlacesListener;

import static android.content.Context.LOCATION_SERVICE;


public class Menu2Fragment extends Fragment implements OnMapReadyCallback, LocationListener, PlacesListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private MapView mapView = null;

    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;
    private boolean isPermission = false;
    private GPSInfo gps;

    private static final String TAG = "@@@";
    private GoogleApiClient mGoogleApiClient = null;
    private LocationRequest mLocationRequest;
    private GoogleMap googleMap;
    LocationManager locationManager;
    MapFragment mapFragment;
    boolean setGPS = false;
    LatLng SEOUL = new LatLng(37.56, 126.97);
    LatLng currentPosition;
    Marker current_marker =null;
    List<Marker> previous_marker = null;
    Dialog loginDialog;


    double latitude = 35; // 제도관 : 35.231009, 129.082252
    double longitude = 129;
    public StringBuilder strBld = new StringBuilder();
    public String addstr="";
    public Menu2Fragment() {}


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //place listener 4개 override
    @Override
    public void onPlacesFailure(PlacesException e) {}

    @Override
    public void onPlacesStart() {}

    @Override
    public void onPlacesSuccess(final List<Place> places) {
        Log.i("PlacesAPI", "onPlacesSuccess()");

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (Place place : places) {
                    LatLng latLng = new LatLng(place.getLatitude(), place.getLongitude());
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title(place.getName());
                    markerOptions.snippet(place.getVicinity());
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(14));
                    Marker item = googleMap.addMarker(markerOptions);
                    previous_marker.add(item);
                }

                //중복 마커 제거
                HashSet<Marker> hashSet = new HashSet<Marker>();
                hashSet.addAll(previous_marker);
                previous_marker.clear();
                previous_marker.addAll(hashSet);
            }
        });
    }

    @Override
    public void onPlacesFinished() {}
    //location listener 4개 override
    @Override
    public void onLocationChanged(Location location) {
        currentPosition = new LatLng( location.getLatitude(), location.getLongitude() );
        String errorMessage = "";
        if (current_marker != null ) current_marker.remove();

        //현재 위치에 마커 생성
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("현재위치");
        current_marker = googleMap.addMarker(markerOptions);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        googleMap.getUiSettings().setCompassEnabled(true);

        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        List<Address> addresses = null;

        try {
            // Using getFromLocation() returns an array of Addresses for the area immediately
            // surrounding the given latitude and longitude. The results are a best guess and are
            // not guaranteed to be accurate.
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    1);
        } catch (IOException ioException) {
            errorMessage = "지오코더 서비스 사용불가";
            Toast.makeText( getActivity(), errorMessage, Toast.LENGTH_LONG).show();
        } catch (IllegalArgumentException illegalArgumentException) {
            errorMessage = "잘못된 GPS 좌표";
            Toast.makeText( getActivity(), errorMessage, Toast.LENGTH_LONG).show();
        }

        // Handle case where no address was found.
        if (addresses == null || addresses.size()  == 0) {
            if (errorMessage.isEmpty()) {
                errorMessage = "주소 미발견";
                Log.e(TAG, errorMessage);
            }
            Toast.makeText( getActivity(), errorMessage, Toast.LENGTH_LONG).show();
        } else {
            Address address = addresses.get(0);
            Toast.makeText( getActivity(), address.getAddressLine(0).toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {}

    @Override
    public void onProviderEnabled(String s) {}

    @Override
    public void onProviderDisabled(String s) {}

    //GoogleApiClient.ConnectionCallbacks 2개
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d( TAG, "onConnected" );

        if ( locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            setGPS = true;

        mLocationRequest = new LocationRequest();
        //mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);


        if (ActivityCompat.checkSelfPermission( getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission( getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            Log.d( TAG, "onConnected " + "getLocationAvailability mGoogleApiClient.isConnected()="+mGoogleApiClient.isConnected() );

            if ( !mGoogleApiClient.isConnected()  ) mGoogleApiClient.connect();

            // LocationAvailability locationAvailability = LocationServices.FusedLocationApi.getLocationAvailability(mGoogleApiClient);

            if ( setGPS && mGoogleApiClient.isConnected()) {//|| locationAvailability.isLocationAvailable() )
                Log.d( TAG, "onConnected " + "requestLocationUpdates" );
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);
                Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                if ( location == null ) return;

                //현재 위치에 마커 생성
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("현재위치");
                googleMap.addMarker(markerOptions);

                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {}

    //GoogleApiClient.OnConnectionFailedListener 1개
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.find, container, false);

        mapView = (MapView)layout.findViewById(R.id.map);
        mapView.getMapAsync(this);
        if(!isPermission) {
            callPermission();
        }
        gps = new GPSInfo(getContext());
        if (gps.isGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            Geocoder gCoder = new Geocoder(getContext(), Locale.getDefault());

            try{
                if(gCoder!=null) {
                    List<Address> addr = gCoder.getFromLocation(latitude,longitude,1);

                    if (addr != null && addr.size() > 0) {
                        Address a = addr.get(0);
                        for (int i=0;i <= a.getMaxAddressLineIndex();i++) {
                            //여기서 변환된 주소 확인할  수 있음
                            strBld.append(a.getAddressLine(i));
                            //Log.v("알림", "AddressLine(" + i + ")" + a.getAddressLine(i) + "\n");
                        }
                    }
                    addstr=strBld.toString();
                }

            } catch (IOException e){
                e.printStackTrace();
            }

        } else {
            gps.showSettingsAlert();
        }
        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        previous_marker = new ArrayList<Marker>();

        Button button = (Button) layout.findViewById(R.id.search_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg) {
                //googleMap.clear();//지도 클리어

                if (previous_marker != null)
                    previous_marker.clear();//지역정보 마커 클리어

                new NRPlaces.Builder()
                        .listener(Menu2Fragment.this)
                        .key("AIzaSyBObMRaYEj2q1CAb_58naLJbQIjKiCGQRU")
                        .latlng(latitude, longitude)
                        .radius(2000)
                        .type(PlaceType.MOVIE_THEATER)
                        .build()
                        .execute();
            }
        });

        return layout;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_ACCESS_FINE_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            isAccessFineLocation = true;
        } else if (requestCode == PERMISSIONS_ACCESS_COARSE_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            isAccessCoarseLocation = true;
        }
        if (isAccessFineLocation && isAccessCoarseLocation) {
            isPermission = true;
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
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
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //액티비티가 처음 생성될 때 실행되는 함수
        if(mapView != null) {
            mapView.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onMapReady(GoogleMap _googleMap) {
        googleMap=_googleMap;
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        //LatLng current = new LatLng(35.231009, 129.082252);
        LatLng current = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(current);
        markerOptions.title("현재위치");
        markerOptions.snippet(addstr);
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(current));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));


        googleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));

        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));


        final GoogleMap finalGoogleMap = googleMap;
        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {

            @Override

            public void onMapLoaded() {

                Log.d( TAG, "onMapLoaded" );

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {}

                else {
                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !setGPS) {
                        Log.d(TAG, "onMapLoaded");
                    }

                    if (mGoogleApiClient == null) {}

                    finalGoogleMap.setMyLocationEnabled(true);
                }
            }
        });

        //구글 플레이 서비스 초기화
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission( getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission( getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                googleMap.setMyLocationEnabled(true);
            }
            else {
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            }
        }
        else {
            googleMap.setMyLocationEnabled(true);
        }
    }
    private void callPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_ACCESS_FINE_LOCATION);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_ACCESS_COARSE_LOCATION);
        } else {
            isPermission = true;
        }
    }
}