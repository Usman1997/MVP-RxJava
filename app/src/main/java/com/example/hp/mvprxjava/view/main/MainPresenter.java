package com.example.hp.mvprxjava.view.main;


import com.example.hp.mvprxjava.model.MovieResponse;
import com.example.hp.mvprxjava.network.MovieService;
import com.example.hp.mvprxjava.network.RetrofitApiClient;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter implements MainContract.mainPresenterInterface {

    private MainContract.mainView mainView;

    public MainPresenter(MainContract.mainView mainView){
        this.mainView = mainView;
    }
    @Override
    public void getMovies() {
        getObservable().subscribeWith(getObserver());
    }


    public io.reactivex.Observable<MovieResponse> getObservable(){
        return RetrofitApiClient.getRetrofit().create(MovieService.class)
                .getMovies("004cbaf19212094e32aa9ef6f6577f22")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<MovieResponse> getObserver(){
        return new DisposableObserver<MovieResponse>() {
            @Override
            public void onNext(MovieResponse movieResponse) {
                mainView.displayMovies(movieResponse);
            }

            @Override
            public void onError(Throwable e) {
                mainView.hideProgressBar();
                mainView.displayError(e.getMessage());
            }

            @Override
            public void onComplete() {
                mainView.hideProgressBar();
            }
        };
    }
}
