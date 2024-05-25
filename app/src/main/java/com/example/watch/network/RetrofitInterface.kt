package com.example.watch.network

import com.example.watch.models.TmdbResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {
    @GET("/")
    fun searchMovie(@Query("apikey") apikey: String, @Query("s") s: String): Observable<TmdbResponse>
}