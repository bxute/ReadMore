package bxute.readmore;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ankit on 8/17/2017.
 */

public class SuggestionAsyncTask extends AsyncTask<String, Void, String[]> {

    private OnSuggestionAvailableListener suggestionListener;

    @Override
    protected String[] doInBackground(String... params) {
        URL url = null;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL("https://suggestqueries.google.com/complete/search?client=books&q=" + URLEncoder.encode(params[0], "utf-8"));
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader r = new BufferedReader(new InputStreamReader(in));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line).append('\n');
            }

            return match(total.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String[] strings) {
        super.onPostExecute(strings);
        if(suggestionListener!=null){
            suggestionListener.onSuggestionsAvailable(strings);
        }
 //       suggestionListView.setAdapter(new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_list_item_1, strings));
    }

    public SuggestionAsyncTask setSuggestionListener(OnSuggestionAvailableListener suggestionListener){
        this.suggestionListener = suggestionListener;
        return this;
    }

    public interface OnSuggestionAvailableListener{
        void onSuggestionsAvailable(String[] suggs);
    }

    private String[] match(String result){
        List<String> allMatches = new ArrayList<String>();
        String[] matches = {};
        Matcher m = Pattern.compile("\"(.*?)\"")
                .matcher(result);
        int i=0;
        while (m.find()) {
            String _match = m.group();
            _match = _match.substring(1,_match.length()-1);
            if(_match.length()>0){
                allMatches.add(_match);
            }
        }
        return allMatches.toArray(new String[allMatches.size()]);
    }

}