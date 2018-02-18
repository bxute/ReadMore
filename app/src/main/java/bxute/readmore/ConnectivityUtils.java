package bxute.readmore;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Ankit on 8/5/2016.
 */
public class ConnectivityUtils {

    private static Context context;
    private static ConnectivityUtils mInstance;

    public ConnectivityUtils(Context context) {
        ConnectivityUtils.context = context;
    }

    public static ConnectivityUtils getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ConnectivityUtils(context);
        }
        return mInstance;
    }

    public static boolean isConnectedToNet() {

        ConnectivityManager
                cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();

    }
}