package com.sample.wewatch.model

import android.util.Log
import com.sample.wewatch.network.RetrofitClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

import io.reactivex.schedulers.Schedulers

open class RemoteDataSource {
    private val TAG = "RemoteDataSource"

  fun searchResultsObservable(query: String): Observable<TmdbResponse> {
      Log.d(TAG, "search/movie")
    return RetrofitClient.moviesApi
        .searchMovie(RetrofitClient.API_KEY, query)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
  }
}