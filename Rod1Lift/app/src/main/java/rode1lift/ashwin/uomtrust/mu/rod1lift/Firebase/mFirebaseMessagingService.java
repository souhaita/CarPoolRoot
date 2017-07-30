package rode1lift.ashwin.uomtrust.mu.rod1lift.Firebase;

/**
 * Created by Ashwin on 30-Jul-17.
 */


import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import rode1lift.ashwin.uomtrust.mu.rod1lift.Activities.ActivityMain;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT;


public class mFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = mFirebaseMessagingService.class.getSimpleName();

    private FirebaseNotificationUtils firebaseNotificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void handleNotification(String message) {
        if (!FirebaseNotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(CONSTANT.PUSH_NOTIFICATION);
            pushNotification.putExtra(CONSTANT.FIREBASE_MESSAGE, message);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(pushNotification);

        }else{
            // If the app is in background, firebase itself handles the notification

        }

        FirebaseNotificationUtils firebaseNotificationUtils = new FirebaseNotificationUtils(getApplicationContext());
        firebaseNotificationUtils.playNotificationSound();
    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());

        try {
            JSONObject data = json.getJSONObject("data");

            String title = data.getString("title");
            String message = data.getString("message");
            boolean isBackground = data.getBoolean("is_background");
            String imageUrl = data.getString("image");
            String timestamp = data.getString("timestamp");
            JSONObject payload = data.getJSONObject("payload");

            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
            Log.e(TAG, "isBackground: " + isBackground);
            Log.e(TAG, "payload: " + payload.toString());
            Log.e(TAG, "imageUrl: " + imageUrl);
            Log.e(TAG, "timestamp: " + timestamp);


            if (!FirebaseNotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(CONSTANT.PUSH_NOTIFICATION);
                pushNotification.putExtra(CONSTANT.FIREBASE_MESSAGE, message);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(pushNotification);

            } else {
                // app is in background, show the notification in notification tray
                Intent resultIntent = new Intent(getApplicationContext(), ActivityMain.class);
                resultIntent.putExtra(CONSTANT.FIREBASE_MESSAGE, message);

                // check for image attachment
                if (TextUtils.isEmpty(imageUrl)) {
                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
                } else {
                    // image is present, show notification with image
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
                }
            }

            // play notification sound
            FirebaseNotificationUtils firebaseNotificationUtils = new FirebaseNotificationUtils(getApplicationContext());
            firebaseNotificationUtils.playNotificationSound();

        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        firebaseNotificationUtils = new FirebaseNotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        firebaseNotificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        firebaseNotificationUtils = new FirebaseNotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        firebaseNotificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
}