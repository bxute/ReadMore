package bxute.readmore.interfaces;

/**
 * Created by Ankit on 8/15/2017.
 */

public interface OnNavigationItemClickListener {
    static final int FAVORITE = 1;
    static final int LOGOUT = 2;
    void onItemClicked(int id);
}
