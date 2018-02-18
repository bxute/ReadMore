package bxute.readmore.api;

import bxute.readmore.models.SearchResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReadMoreAPI {

    @GET("volumes")
    Call<SearchResponse> fetchBooks(@Query("q") String search_term, @Query("printType") String printType, @Query("key") String key);

}
