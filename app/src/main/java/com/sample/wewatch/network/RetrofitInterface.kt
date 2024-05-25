package com.sample.wewatch.network

import com.sample.wewatch.model.TmdbResponse
import com.sample.wewatch.network.RetrofitClient.API_KEY
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface RetrofitInterface {
  @GET("/")
  fun searchMovie(@Query("apikey") api_key: String, @Query("s") s: String): Observable<TmdbResponse>
}
