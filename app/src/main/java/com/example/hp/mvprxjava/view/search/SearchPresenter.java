package com.example.hp.mvprxjava.view.search;

import android.annotation.SuppressLint;
import android.support.v7.widget.SearchView;

import com.example.hp.mvprxjava.model.MovieResponse;
import com.example.hp.mvprxjava.network.MovieService;
import com.example.hp.mvprxjava.network.RetrofitApiClient;

import java.net.NetworkInterface;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class SearchPresenter implements SearchContract.SearchPresenterInterface {
    private SearchContract.SearchViewInterface searchViewInterface;


    public SearchPresenter(SearchContract.SearchViewInterface searchViewInterface){
        this.searchViewInterface = searchViewInterface;
    }
    @SuppressLint("CheckResult")
    @Override
    public void getResultsBasedOnQuery(SearchView searchView) {
        getObservableQuery(searchView).filter(new Predicate<String>() {
            @Override
            public boolean test(String s) throws Exception {
                if(s.equals("")){
                    return false;
                }else{
                    return true;
                }
            }
        }).debounce(2,TimeUnit.SECONDS)
                .distinctUntilChanged()
                .switchMap(new Function<String, ObservableSource<MovieResponse>>() {
                    @Override
                    public ObservableSource<MovieResponse> apply(String s) throws Exception {
                        return RetrofitApiClient.getRetrofit().create(MovieService.class)
                                .getMoviesBasedOnQuery("004cbaf19212094e32aa9ef6f6577f22",s);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver());
    }

    private Observable<String> getObservableQuery(SearchView searchView){
        final PublishSubject<String> publishSubject = PublishSubject.create();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                publishSubject.onNext(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                publishSubject.onNext(s);
                return true;
            }
        });
        return publishSubject;
    }



    public DisposableObserver<MovieResponse> getObserver(){
        return new DisposableObserver<MovieResponse>() {
            @Override
            public void onNext(MovieResponse movieResponse) {
                searchViewInterface.displayResult(movieResponse);

            }

            @Override
            public void onError(Throwable e) {
                searchViewInterface.displayError("Error fetching Movie Data");

            }

            @Override
            public void onComplete() {

            }
        };
    }
}
