package bxute.readmore.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import bxute.readmore.ConnectivityUtils;
import bxute.readmore.DetailsActivity;
import bxute.readmore.FontManager;
import bxute.readmore.R;
import bxute.readmore.interfaces.OverflowIconTapListener;
import bxute.readmore.models.BookModel;

/**
 * Created by Ankit on 8/15/2017.
 */

class BookViewHolder extends RecyclerView.ViewHolder {

    @InjectView(R.id.cover_art)
    SimpleDraweeView coverArt;
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.overflow_icon)
    TextView overflowIcon;
    @InjectView(R.id.author)
    TextView author;

    public BookViewHolder(View itemView) {
        super(itemView);
        ButterKnife.inject(this, itemView);
    }

    public void bind(final Context context, final BookModel book, final OverflowIconTapListener overflowIconTapListener) {

        if (ConnectivityUtils.getInstance(context).isConnectedToNet()) {
            coverArt.setImageURI(book.thumbnailUrl);
        }
        title.setText(book.title);
        author.setText(book.author);
        overflowIcon.setTypeface(new FontManager(context).getTypeFace());
        overflowIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (overflowIconTapListener != null) {
                    overflowIconTapListener.onOverflowTapped(book, overflowIcon);
                }
            }
        });

        coverArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent detailsIntent = new Intent(context, DetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(context.getString(R.string.parcel_key), book);
                detailsIntent.putExtras(bundle);
                context.startActivity(detailsIntent);

            }
        });
    }

}
