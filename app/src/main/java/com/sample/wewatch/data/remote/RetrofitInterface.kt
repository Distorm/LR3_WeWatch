package com.sample.wewatch.data.remote

import com.sample.wewatch.data.model.TmdbResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface RetrofitInterface {
  @GET("/")
  fun searchMovie(@Query("apikey") api_key: String, @Query("s") s: String): Observable<TmdbResponse>
}
