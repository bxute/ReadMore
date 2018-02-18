package bxute.readmore.preference;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

public class PreferenceManager {

    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    private static final String PREF_NAME = "s_pref";
    private static final int PREF_MODE_PRIVATE = 1;

    public PreferenceManager(Context context) {

        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();

    }

    public void clearPreference(){
        editor.clear();
        editor.apply();
    }

    public void setUserEmail(String email){
        editor.putString("email",email);
        editor.apply();
    }

    public String getUserEmail(){
        return preferences.getString("email","");
    }

    public String getUserId(){
        return preferences.getString("userID","");
    }

    public void setUserId(String userId){
        editor.putString("userID",userId);
        editor.apply();
    }

    public String getSearchTerm() {
        return preferences.getString("st","Art");
    }

    public void setSearchTerm(String searchTerm){
        editor.putString("st",searchTerm);
        editor.apply();
    }

    public String getFavoriteBookId(){
        return preferences.getString("fav_id","");
    }

    public void setFavoriteBookId(String bookId){
        editor.putString("fav_id",bookId);
        editor.apply();
    }

    public void setUserPhotoUrl(Uri photoUrl) {
        editor.putString("photo",photoUrl.toString());
        editor.apply();
    }

    public String getUserPhotoUrl(){
        return preferences.getString("photo","");
    }
}
