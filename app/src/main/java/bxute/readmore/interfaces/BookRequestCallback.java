package bxute.readmore.interfaces;

import bxute.readmore.models.SearchResponse;

/**
 * Created by Ankit on 8/16/2017.
 */

public interface BookRequestCallback {
    void onBookRequestFailed();
    void onSuccessBookRequest(SearchResponse response);
}
