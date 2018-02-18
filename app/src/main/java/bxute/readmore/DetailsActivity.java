package bxute.readmore;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import bxute.readmore.data.DatabaseHelper;
import bxute.readmore.data.FavoriteManager;
import bxute.readmore.models.BookModel;

public class DetailsActivity extends AppCompatActivity {

    @InjectView(R.id.cover_art)
    SimpleDraweeView coverArt;
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.author)
    TextView author;
    @InjectView(R.id.pub_date)
    TextView pubDate;
    @InjectView(R.id.favorite_icon)
    TextView favoriteIcon;
    @InjectView(R.id.favorite_caption)
    TextView favoriteCaption;
    @InjectView(R.id.divider)
    FrameLayout divider;
    @InjectView(R.id.description)
    TextView description;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.favorite_wrapper)
    LinearLayout favoriteWrapper;
    private BookModel book;
    private DatabaseHelper databaseHelper;
    private FavoriteManager favoriteManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        book = getIntent().getExtras().getParcelable(getString(R.string.parcel_key));
        getSupportActionBar().setTitle(book.title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        databaseHelper = new DatabaseHelper(this);
        favoriteManager = new FavoriteManager(this);
        bindData();
    }

    private void bindData() {

        if (ConnectivityUtils.getInstance(this).isConnectedToNet()) {
            coverArt.setImageURI(book.thumbnailUrl);
        }
        title.setText(book.title);
        author.setText(book.author);
        pubDate.setText(book.published_date);
        description.setText(book.description);

        int color = databaseHelper.isFavorite(book.id) ? Color.RED : Color.BLACK;
        String text = databaseHelper.isFavorite(book.id)?getString(R.string.rem_fav):getString(R.string.add_fav);
        favoriteCaption.setText(text);
        favoriteIcon.setTextColor(color);

        favoriteIcon.setTypeface(new FontManager(this).getTypeFace());
        favoriteWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (databaseHelper.isFavorite(book.id)) {
                    //remove
                    favoriteManager.removeFavorite(book.id);
                    favoriteIcon.setTextColor(Color.BLACK);
                    favoriteCaption.setText(R.string.add_caption);
                } else {
                    //add
                    favoriteManager.addFavorite(book);
                    favoriteIcon.setTextColor(Color.RED);
                    favoriteCaption.setText(R.string.remove_caption);
                }
            }
        });
    }
}
