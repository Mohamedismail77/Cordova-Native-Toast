package com.plugin.nativetoast;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.plugin.nativetoast.NativeToast;
import android.widget.Toast;


public class LocationProvider {
    private FusedLocationProviderClient mFusedLocationClient;
    public Context mContext;
    public LocationRequest mLocationRequest;
    public LocationCallback mLocationCallback;
    private double mLatitude;
    private double mLongitude;
    private double mAltitude;
    private float mSpeed;
    private float mBearing;
    private float mAccuracy;

    onLoactionUpdated onLoactionUpdated;

    public interface onLoactionUpdated{
        void setOnLocationUpdate(Location location);
    }


    public LocationProvider(Context context, NativeToast nativeToast ) {
        mContext = context;
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1);
        mLocationRequest.setFastestInterval(5);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        onLoactionUpdated = (onLoactionUpdated) nativeToast;
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                    //TODO
                    //save location Location
                    mLatitude = location.getLatitude();
                    mLongitude = location.getLongitude();
                    mAltitude = location.getAltitude();
                    mSpeed = location.getSpeed();
                    mBearing = location.getBearing();
                    mAccuracy = location.getAccuracy();
                    onLoactionUpdated.setOnLocationUpdate(location);

            };
        };
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(mContext,"Permission don't granted",Toast.LENGTH_LONG).show();
            //Ask to get permission

        } else {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest,mLocationCallback,null);
        }
    }


    /**
     * @return the mLatitude
     */
    public double getmLatitude() {
        return mLatitude;
    }

    /**
     * @return the mLongitude
     */
    public double getmLongitude() {
        return mLongitude;
    }

    /**
     * @return the mAltitude
     */
    public double getmAltitude() {
        return mAltitude;
    }

    /**
     * @return the mSpeed
     */
    public float getmSpeed() {
        return mSpeed;
    }

    /**
     * @return the mAccuracy
     */
    public float getmAccuracy() {
        return mAccuracy;
    }

    /**
     * @return the mBearing
     */
    public float getmBearing() {
        return mBearing;
    }


    
}