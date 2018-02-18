package bxute.readmore.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by Ankit on 8/1/2017.
 */

public class MoviesProvider extends ContentProvider {

    static final String PROVIDER_NAME = "com.bxute.MoviesProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/movies";
    static final Uri CONTENT_URI = Uri.parse(URL);
    private static HashMap<String, String> MOVIE_PROJECTION_MAP;

    static final int MOVIES = 1;
    static final int MOVIE_ID = 2;
    private SQLiteDatabase db;

    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "movies", MOVIES);
        uriMatcher.addURI(PROVIDER_NAME, "movie/#", MOVIE_ID);
    }

    @Override
    public boolean onCreate() {
        Context context  =  getContext();
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        db = databaseHelper.getWritableDatabase();
        return (db==null)?false:true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

            SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
            qb.setTables("movies");
            switch (uriMatcher.match(uri)) {
                case MOVIES:
                    qb.setProjectionMap(MOVIE_PROJECTION_MAP);
                    break;

                case MOVIE_ID:
                    qb.appendWhere("id=" + uri.getPathSegments().get(1));
                    break;

                default:
            }
            Cursor c = qb.query(db,	projection,	selection,
                    selectionArgs,null, null, sortOrder);
            c.setNotificationUri(getContext().getContentResolver(), uri);
            return c;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)){
            case MOVIES:
                return "vnd.android.cursor.dir/vnd.bxute.movies";
            case MOVIE_ID:
                return "vnd.android.cursor.item/vnd.bxute.movies";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long id = db.insert("movies",null,values);
        if (id > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, id);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)){
            case MOVIES:
                count = db.delete("movies", selection, selectionArgs);
                break;

            case MOVIE_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete("movies","id=?",new String[]{id});
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
