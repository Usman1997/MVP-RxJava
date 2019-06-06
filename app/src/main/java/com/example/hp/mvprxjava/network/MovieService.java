package com.example.hp.mvprxjava.network;

import com.example.hp.mvprxjava.model.MovieResponse;

import java.util.Observable;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {

    @GET("discover/movie")
    io.reactivex.Observable<MovieResponse> getMovies(
            @Query("api_key") String api_key);

    @GET("search/movie")
    io.reactivex.Observable<MovieResponse> getMoviesBasedOnQuery(
            @Query("api_key") String api_key,
            @Query("query") String q);
}
