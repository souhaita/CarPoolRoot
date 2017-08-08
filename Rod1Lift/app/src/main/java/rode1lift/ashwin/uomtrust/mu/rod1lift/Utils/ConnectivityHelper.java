package rode1lift.ashwin.uomtrust.mu.rod1lift.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Helps to check if device has access to the internet.
 */
public class ConnectivityHelper {

    private static NetworkInfo activeNetwork;

    private static final int TYPE_WIFI = 1;
    private static final int TYPE_MOBILE = 2;
    private static final int TYPE_NOT_CONNECTED = 0;

    private static final String MSG_WIFI        = "Wifi enabled";
    private static final String MSG_MOBILE      = "Mobile data enabled";
    private static final String MSG_NO_INTERNET = "Not connected to Internet";

    private static void setActiveNetwork(final Context context) {

        final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        activeNetwork = cm.getActiveNetworkInfo();
    }

    public static boolean isConnected(final Context context) {

        setActiveNetwork(context);

        return activeNetwork != null && activeNetwork.isConnected();
    }

    private static int getConnectivityStatusCode(final Context context) {

        setActiveNetwork(context);

        if (null != activeNetwork) {

            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }

        return TYPE_NOT_CONNECTED;
    }
}
