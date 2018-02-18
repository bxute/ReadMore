package bxute.readmore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import bxute.readmore.R;
import bxute.readmore.SuggestionAsyncTask;
import bxute.readmore.preference.PreferenceManager;

public class SearchActivity extends AppCompatActivity implements SuggestionAsyncTask.OnSuggestionAvailableListener {

    @InjectView(R.id.search_view_container)
    FrameLayout searchViewContainer;
    @InjectView(R.id.suggestionListView)
    ListView suggestionListView;
    PreferenceManager preferenceManager;
    @InjectView(R.id.search_et)
    EditText searchEt;
    @InjectView(R.id.clearbtn)
    ImageView clearbtn;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.no_title);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        preferenceManager = new PreferenceManager(this);
        suggestionListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[]{}));
        suggestionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String term = (String) parent.getItemAtPosition(position);
                preferenceManager.setSearchTerm(term);
                navigateToHome();
            }
        });

        clearbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEt.setText("");
            }
        });

        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    fireSearch(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void fireSearch(String term) {
        new SuggestionAsyncTask().setSuggestionListener(this).execute(term);
    }

    private void navigateToHome() {

        Intent notificationsPageIntent = new Intent(this, HomeActivity.class);
        startActivity(notificationsPageIntent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();

    }

    @Override
    public void onSuggestionsAvailable(String[] suggs) {
        suggestionListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, suggs));
    }
}
