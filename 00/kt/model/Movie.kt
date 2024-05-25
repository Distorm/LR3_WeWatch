package com.sample.wewatch.model

import androidx.room.Entity
import androidx.room.PrimaryKey

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie_table")
data class Movie(
  @PrimaryKey
  @SerializedName("id")
  @Expose
  var id: Int? = null,
  @SerializedName("imdbID")
  @Expose
  var imdbID: String? = null,
  /*
  @SerializedName("video")
  @Expose
  var video: Boolean? = null,
  @SerializedName("vote_count")
  @Expose
  var voteCount: Int? = null,
  @SerializedName("vote_average")
  @Expose
  var voteAverage: Float? = null,
  */
  @SerializedName("Title")
  @Expose
  var title: String? = null,
  /*
  @SerializedName("popularity")
  @Expose
  var popularity: Float? = null,
  */
  @SerializedName("Poster")
  @Expose
  var posterPath: String? = null,
  /*
  @SerializedName("original_language")
  @Expose
  var originalLanguage: String? = null,
  @SerializedName("original_title")
  @Expose
  var originalTitle: String? = null,
  @SerializedName("genre_ids")
  @Expose
  var genreIds: List<Int>? = null,
  @SerializedName("backdrop_path")
  @Expose
  var backdropPath: String? = null,
  @SerializedName("adult")
  @Expose
  var adult: Boolean? = null,
  */
  @SerializedName("Type")
  @Expose
  var overview: String? = null,
  @SerializedName("Year")
  @Expose
  var releaseDate: String? = null,
  var watched: Boolean = false) {

  fun getReleaseYearFromDate(): String? {
    return releaseDate?.split("-")?.get(0)
  }
}
