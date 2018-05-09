package com.plugin.nativetoast;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.Toast;


/**
 * This class echoes a string called from JavaScript.
 */
public class NativeToast extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("show")) {
            String message = args.getString(0);
            int time = args.getInt(1);
            this.show(message, time, callbackContext);
            return true;
        }
        return false;
    }

    private void show(String message, int time, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
          Context context = this.cordova.getActivity().getApplicationContext();
          Toast toast = Toast.makeText(context, message, time);
          toast.show();
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
}
