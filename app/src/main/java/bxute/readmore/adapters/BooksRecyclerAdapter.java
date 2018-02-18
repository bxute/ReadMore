package bxute.readmore.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import bxute.readmore.data.FavoriteManager;
import bxute.readmore.interfaces.OverflowIconTapListener;
import bxute.readmore.R;
import bxute.readmore.data.BookProvider;
import bxute.readmore.data.DatabaseHelper;
import bxute.readmore.models.BookModel;

/**
 * Created by Ankit on 8/15/2017.
 */

public class BooksRecyclerAdapter extends RecyclerView.Adapter<BookViewHolder> implements OverflowIconTapListener {

    private ArrayList<BookModel> books;
    private Context context;
    private FavoriteManager favoriteManager;

    public BooksRecyclerAdapter(Context context) {
        this.context = context;
        books = new ArrayList<>();
        this.favoriteManager = new FavoriteManager(context);
    }

    public void setBooks(ArrayList<BookModel> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_item, null);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        holder.bind(context, books.get(position), this);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    @Override
    public void onOverflowTapped(BookModel book, View anchorView) {
        showOverflowMenu(book, anchorView);
    }

    private void showOverflowMenu(final BookModel book, View anchorView) {

        PopupMenu menu = new PopupMenu(context, anchorView);
        menu.getMenuInflater().inflate(R.menu.book_options_menu, menu.getMenu());
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.add:
                        showAddFavoriteConfirmationDialog(book);
                        break;
                }
                return true;
            }
        });

        menu.show();
    }

    private void makeToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    private void showAddFavoriteConfirmationDialog(final BookModel book) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.favorite_dialog_title);
        builder.setMessage(context.getString(R.string.add) + book.title + context.getString(R.string.postfix));
        builder.setPositiveButton(R.string.add_btn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                favoriteManager.addFavorite(book);
            }
        });
        builder.setNegativeButton(R.string.cancel_btn, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
