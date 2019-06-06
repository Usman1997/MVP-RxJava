package com.example.hp.mvprxjava.view.main;

import com.example.hp.mvprxjava.model.MovieResponse;

public interface MainContract {

    public interface mainView{
        void showProgressBar();
        void hideProgressBar();
        void displayMovies(MovieResponse movieResponse);
        void displayError(String s);
        void showToast(String s);

    }
    public interface mainPresenterInterface{
        void getMovies();
    }
}
