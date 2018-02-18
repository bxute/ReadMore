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

import java.util.HashMap;

/**
 * Created by Ankit on 8/15/2017.
 */

public class BookProvider extends ContentProvider {

    static final String PROVIDER_NAME = "com.bxute.BookProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/books";
    public static final Uri CONTENT_URI = Uri.parse(URL);
    private SQLiteDatabase database;

    private static final int FAVORITE = 3;
    private static final int FAVORITE_ID = 5;
    private HashMap<String, String> FAVORITE_PROJECTION_MAP;
    static final UriMatcher uriMatcher;


    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "favorites", FAVORITE);
        uriMatcher.addURI(PROVIDER_NAME, "favorite/#", FAVORITE_ID);
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return (database != null);
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(Fields.TABLE_FAVORITES);
        switch (uriMatcher.match(uri)) {
            case FAVORITE:
                sqLiteQueryBuilder.setProjectionMap(FAVORITE_PROJECTION_MAP);
                break;
            case FAVORITE_ID:
                sqLiteQueryBuilder.appendWhere(Fields.ID + "=" + uri.getPathSegments().get(1));
                break;
        }

        Cursor cursor = sqLiteQueryBuilder.query(database, projection, selection, selectionArgs, null, null, null);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case FAVORITE:
                return "vnd.android.cursor.dir/vnd.bxute.favorites";
            case FAVORITE_ID:
                return "vnd.android.cursor.item/vnd.bxute.favorites";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        long insertId = database.insert(Fields.TABLE_FAVORITES, null, values);

        if (insertId > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, insertId);
            getContext().getContentResolver().notifyChange(uri, null);
            return _uri;
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        int count = 0;
        count = database.delete(Fields.TABLE_FAVORITES, selection+"=?", selectionArgs);
        return count;

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
