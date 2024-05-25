package com.example.watch.models

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

    @SerializedName("Title")
    @Expose
    var title: String? = null,

    @SerializedName("Poster")
    @Expose
    var posterPath: String? = null,

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