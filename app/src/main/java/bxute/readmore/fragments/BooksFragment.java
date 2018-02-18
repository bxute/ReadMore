package bxute.readmore.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import bxute.readmore.R;
import bxute.readmore.adapters.BooksRecyclerAdapter;
import bxute.readmore.data.BookFlavors;
import bxute.readmore.data.DataServer;
import bxute.readmore.interfaces.BookRequestCallback;
import bxute.readmore.models.BookModel;
import bxute.readmore.models.SearchResponse;
import bxute.readmore.preference.PreferenceManager;
import bxute.readmore.view.BookCardDecoration;

public class BooksFragment extends Fragment implements BookRequestCallback {


    @InjectView(R.id.books_recycler_view)
    RecyclerView booksRecyclerView;
    @InjectView(R.id.progressBar)
    ProgressBar progressBar;
    private BooksRecyclerAdapter booksRecyclerAdapter;
    private Context context;
    ArrayList<BookModel> books;
    private PreferenceManager preferenceManager;

    public BooksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        booksRecyclerAdapter = new BooksRecyclerAdapter(context);
        preferenceManager = new PreferenceManager(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_books, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int col = getResources().getInteger(R.integer.columns);
        booksRecyclerView.setLayoutManager(new GridLayoutManager(context, col));
        int spacingInPixels = 20;
        booksRecyclerView.addItemDecoration(new BookCardDecoration(spacingInPixels));
        booksRecyclerView.setAdapter(booksRecyclerAdapter);
        requestBooks();
    }

    private void requestBooks() {
        DataServer.requestBook(preferenceManager.getSearchTerm(), BookFlavors.PRINT_BOOK, this);
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


    @Override
    public void onBookRequestFailed() {
        // TODO: 8/16/2017 show error
    }

    @Override
    public void onSuccessBookRequest(SearchResponse response) {
        // populate the adapter
        books = new ArrayList<>();
        int size = 0;
        try {
            size = response.mItems.size();
        } catch (Exception e) {

        }

        for (int i = 0; i < size; i++) {
            SearchResponse.Items item = response.mItems.get(i);
            String author = "";
            String image = "";
            try {
                author += item.mVolumeinfo.mAuthors.get(0);
                image = item.mVolumeinfo.mImagelinks.mThumbnail;
            } catch (Exception e) {

            }

            books.add(new BookModel(
                    item.mId,
                    item.mVolumeinfo.mTitle,
                    image,
                    author,
                    item.mVolumeinfo.mDescription,
                    item.mVolumeinfo.mPublisheddate
            ));
        }

        progressBar.setVisibility(View.INVISIBLE);
        booksRecyclerAdapter.setBooks(books);

    }
}
