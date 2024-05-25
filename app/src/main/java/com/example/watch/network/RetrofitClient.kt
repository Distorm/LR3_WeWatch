package com.example.watch.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    const val API_KEY = "297a18d2"
    private const val TMDB_BASE_URL = "https://www.omdbapi.com/"
    const val TMDB_IMAGEURL = ""

    val moviesApi: RetrofitInterface = Retrofit.Builder()
        .baseUrl(TMDB_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(RetrofitInterface::class.java)
}