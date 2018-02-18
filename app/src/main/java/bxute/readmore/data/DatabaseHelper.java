package bxute.readmore.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import bxute.readmore.models.BookModel;

/**
 * Created by Ankit on 8/15/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "bookDB";
    private static final int VER = 1;

    private final String CREATE_TABLE_FAVORITES_SCRIPT = "create table " + Fields.TABLE_FAVORITES + "(" +
            Fields.ID + " text unique," +
            Fields.TITLE + " text," +
            Fields.AUTHOR + " text," +
            Fields.DESCRIPTION + " text," +
            Fields.PUBLISHED_DATE + " text," +
            Fields.THUMBNAIL + " text);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FAVORITES_SCRIPT);
        Log.d("DEBUG", "created favorites table");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public static ContentValues getCVObject(BookModel book) {

        ContentValues values = new ContentValues();
        values.put(Fields.ID, book.id);
        values.put(Fields.TITLE, book.title);
        values.put(Fields.AUTHOR, book.author);
        values.put(Fields.DESCRIPTION, book.description);
        values.put(Fields.PUBLISHED_DATE, book.published_date);
        values.put(Fields.THUMBNAIL, book.thumbnailUrl);
        return values;

    }

    public boolean isFavorite(String bookId) {

        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.query(Fields.TABLE_FAVORITES, new String[]{Fields.ID}, Fields.ID+"=?", new String[]{bookId}, null, null, null);
        if (c.moveToFirst()) {
            if (c.getCount() > 0)
                return true;
        }
        return false;
    }

    public BookModel getBook(String bookId){

        BookModel book = null;
        SQLiteDatabase database = getReadableDatabase();
        String[] projection = {
                Fields.ID,
                Fields.THUMBNAIL,
                Fields.PUBLISHED_DATE,
                Fields.AUTHOR,
                Fields.DESCRIPTION,
                Fields.TITLE};

        Cursor c = database.query(Fields.TABLE_FAVORITES,projection,Fields.ID+"=?",new String[]{bookId},null,null,null);

        c.moveToFirst();

        if(c.getCount()>0){

            book = new BookModel(
                    c.getString(c.getColumnIndex(Fields.ID)),
                    c.getString(c.getColumnIndex(Fields.TITLE)),
                    c.getString(c.getColumnIndex(Fields.THUMBNAIL)),
                    c.getString(c.getColumnIndex(Fields.AUTHOR)),
                    c.getString(c.getColumnIndex(Fields.DESCRIPTION)),
                    c.getString(c.getColumnIndex(Fields.PUBLISHED_DATE))
                    );

        }

        return book;
    }

    public void clearDB(){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("DELETE FROM "+Fields.TABLE_FAVORITES);
    }

}
