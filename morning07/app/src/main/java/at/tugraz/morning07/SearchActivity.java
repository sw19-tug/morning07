package at.tugraz.morning07;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;

public class SearchActivity extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);
        //setContentView(R.layout.search);

        handleIntent(getIntent());
    }

    private void searchForImageName(String query) {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchForImageName(query);
        }
    }
}