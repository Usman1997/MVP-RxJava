package com.example.hp.mvprxjava.view.search;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hp.mvprxjava.R;
import com.example.hp.mvprxjava.adapter.MovieAdapter;
import com.example.hp.mvprxjava.model.MovieResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class SearchActivity extends AppCompatActivity implements SearchContract.SearchViewInterface{

    @BindView(R.id.rvMovies)
    RecyclerView rvQueryResult;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private SearchView searchView;
    SearchPresenter searchPresenter;

    MovieAdapter movieAdapter;

    Unbinder unbinder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        unbinder = ButterKnife.bind(this);


        setupViews();
        setupMVP();
    }

    private void setupViews() {
        setSupportActionBar(toolbar);
        rvQueryResult.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupMVP(){
        searchPresenter = new SearchPresenter(this);
    }

    @Override
    public void showToast(String str) {
        Toast.makeText(SearchActivity.this,str,Toast.LENGTH_LONG).show();

    }

    @Override
    public void displayResult(MovieResponse movieResponse) {
        movieAdapter = new MovieAdapter(SearchActivity.this,movieResponse.getResults());
        rvQueryResult.setAdapter(movieAdapter);
    }

    @Override
    public void displayError(String s) {
        showToast(s);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search,menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Enter Movie name..");

        searchPresenter.getResultsBasedOnQuery(searchView);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.action_search){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
