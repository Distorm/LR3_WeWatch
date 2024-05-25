package com.example.watch.models

import android.util.Log
import com.example.watch.network.RetrofitClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

import io.reactivex.schedulers.Schedulers

open class RemoteDataSource {
    private val tag = "RemoteDataSource"

    fun searchResultsObservable(query: String): Observable<TmdbResponse> {
        Log.d(tag, "search/movie")
        return RetrofitClient.moviesApi
            .searchMovie(RetrofitClient.API_KEY, query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}