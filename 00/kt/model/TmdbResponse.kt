package com.sample.wewatch.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TmdbResponse {
  /*
  @SerializedName("page")
  @Expose
  var page: Int? = null
  */
  @SerializedName("totalResults")
  @Expose
  var totalResults: Int? = null
  /*
  @SerializedName("total_pages")
  @Expose
  var totalPages: Int? = null
  */
  @SerializedName("Response")
  @Expose
  var response: Boolean? = false
  @SerializedName("Search")
  @Expose
  var results: List<Movie>? = null
}