package com.sample.wewatch.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // https://www.omdbapi.com/?apikey=1dbc7755&s=in%20a&y=2022&page=2
    // https://omdbapi.com/?apikey=1dbc7755&s=Dud

    const val API_KEY = "1dbc7755"
    const val TMDB_BASE_URL = "https://www.omdbapi.com/"
    const val TMDB_IMAGEURL = ""
    //const val TMDB_IMAGEURL = "https://m.media-amazon.com/images/M/"

/*
  const val API_KEY = "e764a27cb17b01f54152a69437559e46"
  const val TMDB_BASE_URL = "http://api.themoviedb.org/3/"
  const val TMDB_IMAGEURL = "https://image.tmdb.org/t/p/w500/"
*/
  val moviesApi = Retrofit.Builder()
      .baseUrl(TMDB_BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .build()
      .create(RetrofitInterface::class.java)
}
