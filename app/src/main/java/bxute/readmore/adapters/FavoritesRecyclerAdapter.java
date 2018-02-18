package bxute.readmore.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import bxute.readmore.R;
import bxute.readmore.data.FavoriteManager;
import bxute.readmore.interfaces.OverflowIconTapListener;
import bxute.readmore.models.BookModel;

/**
 * Created by Ankit on 8/16/2017.
 */

public class FavoritesRecyclerAdapter extends RecyclerView.Adapter<BookViewHolder> implements OverflowIconTapListener {

    private ArrayList<BookModel> books;
    private Context context;
    private FavoriteManager favoriteManager;
    private OnItemRemovedListener itemRemoveListener;

    public FavoritesRecyclerAdapter(Context context) {
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
        menu.getMenuInflater().inflate(R.menu.remove_options_menu, menu.getMenu());
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.remove:
                        showRemoveFavoriteConfirmationDialog(book);
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

    private void showRemoveFavoriteConfirmationDialog(final BookModel book) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.favorite_dialog_title));
        builder.setMessage(context.getString(R.string.remove_prefix) + book.title + context.getString(R.string.rem_fav_postix));
        builder.setPositiveButton(R.string.remove, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                favoriteManager.removeFavorite(book.id);
                if(itemRemoveListener!=null){
                    itemRemoveListener.onRemoved();
                }
            }
        });
        builder.setNegativeButton(context.getString(R.string.cancel_btn), null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void setOnItemRemovedListener(OnItemRemovedListener itemRemovedListener){
        this.itemRemoveListener = itemRemovedListener;
    }

    public interface OnItemRemovedListener{
        void onRemoved();
    }
}

