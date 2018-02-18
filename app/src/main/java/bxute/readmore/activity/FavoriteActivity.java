package bxute.readmore.activity;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import bxute.readmore.R;
import bxute.readmore.adapters.FavoritesRecyclerAdapter;
import bxute.readmore.data.BookProvider;
import bxute.readmore.data.Fields;
import bxute.readmore.models.BookModel;
import bxute.readmore.view.BookCardDecoration;

public class FavoriteActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, FavoritesRecyclerAdapter.OnItemRemovedListener {

    @InjectView(R.id.favorite_recycler_view)
    RecyclerView booksRecyclerView;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.fav_msg)
    TextView favMsg;
    private FavoritesRecyclerAdapter favoritesRecyclerAdapter;
    ArrayList<BookModel> books;

    private static final int LOADER_ID = 199;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.favorite_page_title);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getLoaderManager().initLoader(LOADER_ID, null, this);
        favoritesRecyclerAdapter = new FavoritesRecyclerAdapter(this);
        booksRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        int spacingInPixels = 20;
        booksRecyclerView.addItemDecoration(new BookCardDecoration(spacingInPixels));
        favoritesRecyclerAdapter.setOnItemRemovedListener(this);
        booksRecyclerView.setAdapter(favoritesRecyclerAdapter);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        CursorLoader loader = null;
        String[] projection = {
                Fields.ID,
                Fields.THUMBNAIL,
                Fields.PUBLISHED_DATE,
                Fields.PUBLISHED_DATE,
                Fields.AUTHOR,
                Fields.DESCRIPTION,
                Fields.TITLE};

        if (id == LOADER_ID) {
            loader = new CursorLoader(this, BookProvider.CONTENT_URI, projection, null, null, null);
        }

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        ArrayList<BookModel> books = new ArrayList<>();
        data.moveToFirst();
        boolean isNext = data.getCount() > 0;
        int visibility = isNext? View.INVISIBLE:View.VISIBLE;
        favMsg.setVisibility(visibility);

        while (isNext) {

            books.add(new BookModel(
                    data.getString(data.getColumnIndex(Fields.ID)),
                    data.getString(data.getColumnIndex(Fields.TITLE)),
                    data.getString(data.getColumnIndex(Fields.THUMBNAIL)),
                    data.getString(data.getColumnIndex(Fields.AUTHOR)),
                    data.getString(data.getColumnIndex(Fields.DESCRIPTION)),
                    data.getString(data.getColumnIndex(Fields.PUBLISHED_DATE))));
            isNext = data.moveToNext();
        }

        favoritesRecyclerAdapter.setBooks(books);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onRemoved() {
        getLoaderManager().restartLoader(LOADER_ID, null, this);
    }
}
