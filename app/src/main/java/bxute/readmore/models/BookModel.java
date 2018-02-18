package bxute.readmore.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Ankit on 8/15/2017.
 */

@IgnoreExtraProperties
public class BookModel implements Parcelable {

    public String id;
    public String title;
    public String thumbnailUrl;
    public String author;
    public String description;
    public String published_date;

    public BookModel() {
    }

    public BookModel(String id, String title, String thumbnailUrl, String author, String description, String published_date) {
        this.id = id;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.author = author;
        this.description = description;
        this.published_date = published_date;
    }


    protected BookModel(Parcel in) {
        id = in.readString();
        title = in.readString();
        thumbnailUrl = in.readString();
        author = in.readString();
        description = in.readString();
        published_date = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(thumbnailUrl);
        dest.writeString(author);
        dest.writeString(description);
        dest.writeString(published_date);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<BookModel> CREATOR = new Parcelable.Creator<BookModel>() {
        @Override
        public BookModel createFromParcel(Parcel in) {
            return new BookModel(in);
        }

        @Override
        public BookModel[] newArray(int size) {
            return new BookModel[size];
        }
    };
}

