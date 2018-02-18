package bxute.readmore.data;

import android.util.Log;

import bxute.readmore.BuildConfig;
import bxute.readmore.api.ReadMoreAPI;
import bxute.readmore.api.ReadMoreApiClient;
import bxute.readmore.interfaces.BookRequestCallback;
import bxute.readmore.models.SearchResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ankit on 8/15/2017.
 */

public class DataServer {

    private static final String TAG = DataServer.class.getSimpleName();
    private static ReadMoreAPI readMoreAPI = null;


    private static ReadMoreAPI getService() {

        readMoreAPI = ReadMoreApiClient.getClient()
                .create(ReadMoreAPI.class);
        return readMoreAPI;

    }

    public static void requestBook(String term , String printType, final BookRequestCallback bookRequestCallback) {

        getService()
                .fetchBooks(term, printType, BuildConfig.API_KEY)
                .enqueue(new Callback<SearchResponse>() {
                    @Override
                    public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                        bookRequestCallback.onSuccessBookRequest(response.body());
                    }

                    @Override
                    public void onFailure(Call<SearchResponse> call, Throwable t) {
                        bookRequestCallback.onBookRequestFailed();
                    }
                });

    }

}
