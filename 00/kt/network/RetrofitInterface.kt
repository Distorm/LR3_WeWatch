package com.sample.wewatch.network

import com.sample.wewatch.model.TmdbResponse
import com.sample.wewatch.network.RetrofitClient.API_KEY
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

// const val API_KEY = "e764a27cb17b01f54152a69437559e46"
// https://api.themoviedb.org/3/search/movie?api_key=<<api_key>>&language=en-US&page=1&include_adult=false
// https://api.themoviedb.org/3/search/movie?api_key=e764a27cb17b01f54152a69437559e46&language=en-US&query=in%20a&page=1&include_adult=false&region=en&year=2022

// https://omdbapi.com/?apikey=1dbc7755&s=Dud

interface RetrofitInterface {
  @GET("/")
  fun searchMovie(@Query("apikey") api_key: String, @Query("s") s: String): Observable<TmdbResponse>
}
