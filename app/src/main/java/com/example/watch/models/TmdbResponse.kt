package com.example.watch.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TmdbResponse {
    @SerializedName("totalResults")
    @Expose
    var totalResults: Int? = null

    @SerializedName("Response")
    @Expose
    var response: Boolean? = false

    @SerializedName("Search")
    @Expose
    var results: List<Movie>? = null
}