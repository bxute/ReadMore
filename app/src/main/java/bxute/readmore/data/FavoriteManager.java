package bxute.readmore.data;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import bxute.readmore.ConnectivityUtils;
import bxute.readmore.R;
import bxute.readmore.models.BookModel;
import bxute.readmore.preference.PreferenceManager;
import bxute.readmore.widget.FavoriteBookWidgetProvider;

/**
 * Created by Ankit on 8/16/2017.
 */

public class FavoriteManager {

    private Context context;
    private FirebaseDataManager firebaseDataManager;
    private PreferenceManager preferenceManager;

    public FavoriteManager(Context context) {
        this.context = context;
        firebaseDataManager = new FirebaseDataManager(context);
        preferenceManager = new PreferenceManager(context);
    }

    public void addFavorite(BookModel book){

        if(ConnectivityUtils.getInstance(context).isConnectedToNet()){
            // add to local
            Uri uri = context.getContentResolver().insert(BookProvider.CONTENT_URI, DatabaseHelper.getCVObject(book));
            if (uri != null) {
                makeToast(context.getString(R.string.added) + book.title + context.getString(R.string.postfix));
            } else {
                makeToast(context.getString(R.string.already_added));
            }

            firebaseDataManager.addFavorite(book);
            preferenceManager.setFavoriteBookId(book.id);
            notifyUpdate();

        }else{
            makeToast(context.getString(R.string.cannot_add_error));
        }
    }


    private void notifyUpdate() {

        // refrenced from stackoverflow

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisWidget = new ComponentName(context, FavoriteBookWidgetProvider.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        Intent intent = new Intent(context, FavoriteBookWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        context.sendBroadcast(intent);
        Toast.makeText(context, R.string.updated_widget,Toast.LENGTH_LONG).show();

    }


    public void removeFavorite(String bookId){

        if(ConnectivityUtils.getInstance(context).isConnectedToNet()){

            // add to local
           int count =  context.getContentResolver().delete(BookProvider.CONTENT_URI,Fields.ID,new String[]{bookId});
            if (count>0) {
                makeToast(context.getString(R.string.removed_from_fav));
            } else {
                makeToast(context.getString(R.string.cannot_remove_fav));
            }
            firebaseDataManager.removeFavorite(bookId);

        }else{
            makeToast(context.getString(R.string.cannot_add_fav_internet_error));
        }
    }

    private void makeToast(String msg){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }

}
