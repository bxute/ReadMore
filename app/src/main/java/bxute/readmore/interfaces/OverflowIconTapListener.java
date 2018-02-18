package bxute.readmore.interfaces;

import android.view.View;

import bxute.readmore.models.BookModel;

/**
 * Created by Ankit on 8/15/2017.
 */

public interface OverflowIconTapListener {
    void onOverflowTapped(BookModel book, View anchorView);
}

