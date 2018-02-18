package bxute.readmore.data;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import bxute.readmore.models.BookModel;
import bxute.readmore.preference.PreferenceManager;

/**
 * Created by Ankit on 8/16/2017.
 */

public class FirebaseDataManager {

    private DatabaseReference mDatabase;
    String userId = "";
    Context context;
    private PreferenceManager preferenceManager;

    public FirebaseDataManager(Context context) {
        preferenceManager = new PreferenceManager(context);
        userId = preferenceManager.getUserId();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        this.context = context;
    }

    public void addFavorite(BookModel bookModel){
        mDatabase.child(bookModel.id).setValue(bookModel);
    }

    public void removeFavorite(String bookId){
        mDatabase.child(bookId).removeValue();
    }

    public void syncFavorite(){

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //recordData((Map<String,Object>) dataSnapshot.getValue());
                for(DataSnapshot d:dataSnapshot.getChildren()){
                    //Log.d("DEBUG",d.getKey().toString());
                    recordData((Map<String, Object>) d.getValue());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void recordData(Map<String, Object> book) {

        BookModel bookModel = null;
        String thumb = "";
        String des = "" ;
        try{
            thumb = book.get(Fields.THUMBNAIL).toString();
            des = book.get(Fields.DESCRIPTION).toString();
        }catch (Exception e){

        }
            bookModel = new BookModel(
                    book.get(Fields.ID).toString(),
                    book.get(Fields.TITLE).toString(),
                    thumb,
                    book.get(Fields.AUTHOR).toString(),
                    des,
                    book.get(Fields.PUBLISHED_DATE).toString()
                    );

            Uri uri = context.getContentResolver().insert(BookProvider.CONTENT_URI, DatabaseHelper.getCVObject(bookModel));

            if(uri!=null){
                preferenceManager.setFavoriteBookId(bookModel.id);
            }

        }

}
