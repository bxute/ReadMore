package bxute.readmore.fragments;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceGroup;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import bxute.readmore.FontManager;
import bxute.readmore.interfaces.OnNavigationItemClickListener;
import bxute.readmore.R;
import bxute.readmore.preference.PreferenceManager;

public class NavigationFragment extends Fragment {


    @InjectView(R.id.header_image)
    SimpleDraweeView headerImage;
    @InjectView(R.id.user_email)
    TextView userEmail;
    @InjectView(R.id.favorite_icon)
    TextView favoriteIcon;
    @InjectView(R.id.favorites)
    TextView favorites;
    @InjectView(R.id.logout_icon)
    TextView logoutIcon;
    @InjectView(R.id.logout)
    TextView logout;
    @InjectView(R.id.logout_wrapper)
    RelativeLayout logoutWrapper;
    @InjectView(R.id.favorites_wrapper)
    RelativeLayout favoritesWrapper;
    private Context context;

    private OnNavigationItemClickListener navigationItemClickListener;
    private PreferenceManager preferenceManager;
    public NavigationFragment() {
        // Required empty public constructor
    }

    public void setOnNavigationItemClickListener(OnNavigationItemClickListener navigationItemClickListener) {
        this.navigationItemClickListener = navigationItemClickListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        preferenceManager = new PreferenceManager(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_navigation, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // set fonts
        Typeface material = new FontManager(context).getTypeFace();
        favoriteIcon.setTypeface(material);
        logoutIcon.setTypeface(material);
        userEmail.setText(preferenceManager.getUserEmail());
        headerImage.setImageURI(preferenceManager.getUserPhotoUrl());
        //attach Listeners
        favoritesWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(navigationItemClickListener!=null)
                    navigationItemClickListener.onItemClicked(OnNavigationItemClickListener.FAVORITE);
            }
        });

        logoutWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(navigationItemClickListener!=null)
                    navigationItemClickListener.onItemClicked(OnNavigationItemClickListener.LOGOUT);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

}
