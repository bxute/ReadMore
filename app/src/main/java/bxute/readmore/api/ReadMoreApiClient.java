package bxute.readmore.api;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReadMoreApiClient {

    private static Retrofit retrofit = null;
    private static final String TAG = ReadMoreApiClient.class.getSimpleName();

    public static Retrofit getClient() {

        retrofit = new Retrofit.Builder()
                .baseUrl(URLS.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Log.d(TAG, "Build Retrofit");
        return retrofit;

    }
}
