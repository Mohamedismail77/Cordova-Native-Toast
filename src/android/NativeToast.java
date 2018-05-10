package com.plugin.nativetoast;

import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;
import android.widget.Toast;
import android.content.Context;
import com.plugin.nativetoast.LocationProvider;


/**
 * This class echoes a string called from JavaScript.
 */
public class NativeToast extends CordovaPlugin implements LocationProvider.onLoactionUpdated {
    public LocationProvider locationProvider;
    public CallbackContext updateLocation;
    public CordovaWebView cordovaWebView;


    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        Context context = this.cordova.getActivity().getApplicationContext();
        locationProvider = new LocationProvider(context,this);
        cordovaWebView = webView;
    }


    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("show")) {
            String message = args.getString(0);
            int time = args.getInt(1);
            this.show(message, time, callbackContext);
            PluginResult pluginResult = new  PluginResult(PluginResult.Status.NO_RESULT);
            pluginResult.setKeepCallback(true);
            callbackContext.sendPluginResult(pluginResult);
            return true;
        }
        return false;
    }

    private void show(String message, int time, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
          Context context = this.cordova.getActivity().getApplicationContext();
          Toast toast = Toast.makeText(context, message, time);
          toast.show();
          PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, "Lat: "+ locationProvider.getmLatitude() + "Long: " + locationProvider.getmLongitude());
          pluginResult.setKeepCallback(true); // keep callback
          callbackContext.sendPluginResult(pluginResult);
          updateLocation = callbackContext;

        } else {
        }
    }

    @Override
    public void setOnLocationUpdate(Location location) {

        
        if(updateLocation != null){
            PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, "Lat: "+ locationProvider.getmLatitude() + "Long: " + locationProvider.getmLongitude());
            pluginResult.setKeepCallback(true); // keep callback
            updateLocation.sendPluginResult(pluginResult);
        }


    }
}
