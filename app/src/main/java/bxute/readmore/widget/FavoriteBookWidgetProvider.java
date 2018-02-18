package bxute.readmore.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import bxute.readmore.ConnectivityUtils;
import bxute.readmore.R;
import bxute.readmore.data.DatabaseHelper;
import bxute.readmore.models.BookModel;
import bxute.readmore.preference.PreferenceManager;


/**
 * Created by Ankit on 8/8/2017.
 */

public class FavoriteBookWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {

        PreferenceManager preferenceManager = new PreferenceManager(context);
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        BookModel bookModel = databaseHelper.getBook(preferenceManager.getFavoriteBookId());
        if(bookModel==null)
            return;

        ComponentName thisWidget = new ComponentName(context, FavoriteBookWidgetProvider.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        for (int widgetId : allWidgetIds) {

            final RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_layout);
            remoteViews.setTextViewText(R.id.title,bookModel.title);
            remoteViews.setTextViewText(R.id.auth,bookModel.author);
            if(ConnectivityUtils.getInstance(context).isConnectedToNet())
                Picasso.with(context)
                    .load(bookModel.thumbnailUrl)
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            remoteViews.setImageViewBitmap(R.id.cover_art,bitmap);
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });

            Intent intent = new Intent(context, FavoriteBookWidgetProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.title, pendingIntent);

            appWidgetManager.updateAppWidget(widgetId, remoteViews);

        }
    }

}
