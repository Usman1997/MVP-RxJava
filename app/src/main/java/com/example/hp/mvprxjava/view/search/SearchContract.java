package com.example.hp.mvprxjava.view.search;

import android.support.v7.widget.SearchView;

import com.example.hp.mvprxjava.model.MovieResponse;

public interface SearchContract {

    public interface SearchViewInterface{
        void showToast(String str);
        void displayResult(MovieResponse movieResponse);
        void displayError(String s);
    }

    public interface SearchPresenterInterface{
        void getResultsBasedOnQuery(SearchView searchView);
    }
}
