package com.sample.wewatch.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    const val API_KEY = "ba16b9bd"
    const val URL = "https://www.omdbapi.com/"
    const val TMDB_IMAGEURL = ""


  val moviesApi = Retrofit.Builder()
      .baseUrl(URL)
      .addConverterFactory(GsonConverterFactory.create())
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .build()
      .create(RetrofitInterface::class.java)
}
