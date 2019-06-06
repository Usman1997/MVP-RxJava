package com.example.hp.mvprxjava.view.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hp.mvprxjava.R;
import com.example.hp.mvprxjava.adapter.MovieAdapter;
import com.example.hp.mvprxjava.model.MovieResponse;
import com.example.hp.mvprxjava.view.search.SearchActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements MainContract.mainView {


    @BindView(R.id.rvMovies)
    RecyclerView recyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.toolbar)
    Toolbar toolbar;



    private String TAG = "MainActivity";

    MovieAdapter movieAdapter;
    LinearLayoutManager linearLayoutManager;
    MainPresenter mainPresenter;
    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        setUpMvp();
        setUpRecyclerView();
        getMovieList();


    }



    private void setUpRecyclerView() {
        setSupportActionBar(toolbar);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
    private void setUpMvp() {
        mainPresenter = new MainPresenter(this);

    }
    private void getMovieList() {
        mainPresenter.getMovies();
    }


    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void displayMovies(MovieResponse movieResponse) {
      if(movieResponse!=null){
          movieAdapter = new MovieAdapter(MainActivity.this,movieResponse.getResults());
          recyclerView.setAdapter(movieAdapter);
          }
    }

    @Override
    public void displayError(String s) {
        showToast(s);
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.search){
            showToast("Search Clicked");
            Intent i = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
