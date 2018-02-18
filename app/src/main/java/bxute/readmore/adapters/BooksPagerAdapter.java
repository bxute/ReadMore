package bxute.readmore.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import bxute.readmore.R;
import bxute.readmore.fragments.BooksFragment;
import bxute.readmore.fragments.MagazinesFragment;

/**
 * Created by Ankit on 8/15/2017.
 */

public class BooksPagerAdapter extends FragmentStatePagerAdapter{

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public BooksPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mFragmentList.add(new BooksFragment());
        mFragmentList.add(new MagazinesFragment());
        mFragmentTitleList.add(context.getString(R.string.book_fragment_title));
        mFragmentTitleList.add(context.getString(R.string.magazines_page__title));

    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
