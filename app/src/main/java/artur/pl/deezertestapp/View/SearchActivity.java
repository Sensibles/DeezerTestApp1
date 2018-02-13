package artur.pl.deezertestapp.View;

import android.app.SearchManager;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import artur.pl.deezertestapp.DeezerTestApp;
import artur.pl.deezertestapp.Model.Entity.HistoryItem;
import artur.pl.deezertestapp.R;
import artur.pl.deezertestapp.View.Utils.Adapters.HistoryListAdapter;
import artur.pl.deezertestapp.View.Utils.ItemClickListener;
import artur.pl.deezertestapp.ViewModel.SearchViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Intent.ACTION_SEARCH;
import static artur.pl.deezertestapp.Constants.SEARCH_ITEM;

public class SearchActivity extends AppCompatActivity implements ItemClickListener {
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private  SearchView searchView;
    private SearchViewModel searchViewModel;
    private HistoryListAdapter historyListAdapter;
    private LiveData<List<HistoryItem>> historyItemLiveData;

    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;

    @BindView(R.id.historyListRecyclerView)
    RecyclerView historyListRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ((DeezerTestApp) getApplication())
                .getApplicationComponent()
                .inject(this);
        ButterKnife.bind(this);

        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        historyListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    protected void onStart() {
        super.onStart();
        setupViewModel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        setupSearchView(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_clear_history:
                searchViewModel.clearHistory();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupObserver(String text){
        if(historyItemLiveData != null)
            historyItemLiveData.removeObservers(this);
        historyItemLiveData = searchViewModel.getHistoryItemsForText(text);
        historyItemLiveData.observe(this, new Observer<List<HistoryItem>>() {
            @Override
            public void onChanged(@Nullable List<HistoryItem> historyItems) {
                if(historyItems != null) {
                    historyListAdapter = new HistoryListAdapter(historyItems, SearchActivity.this);
                    historyListRecyclerView.setAdapter(historyListAdapter);
                }
            }
        });
    }

    private void setupViewModel(){
        searchViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(SearchViewModel.class);
       setupObserver(null);
    }
    /*
        My goal:
        Expanded searchview with no way to collapse it by user.
        I didn't achieve way to disable collapse action by user. setOnActionExpandListener or setOnCloseListener doesn't work.
        TODO: Try to find a way to fix this. Maybe upgrading gradle or something will work.
         */
    private void setupSearchView(Menu menu){
        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search_view).getActionView();
        ComponentName componentName = new ComponentName(this.getApplication(), SearchResultActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));
        searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                searchViewModel.insertHistoryItem(new HistoryItem(query, new Date()));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 0)
                    setupObserver(newText);
                else
                    setupObserver(null);
                return false;
            }
        });

        MenuItem searchMenuItem = menu.findItem(R.id.search_view);
        searchMenuItem.expandActionView();
    }

    @Override
    public void onItemClick(int code, Object o) {
        switch (code) {
            case SEARCH_ITEM:
                Intent intent = new Intent(this, SearchResultActivity.class);
                intent.setAction(ACTION_SEARCH);
                intent.putExtra(SearchManager.QUERY, (String) o);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
