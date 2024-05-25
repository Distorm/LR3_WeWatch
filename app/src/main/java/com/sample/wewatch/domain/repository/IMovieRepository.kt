package com.sample.wewatch.domain.repository

import com.sample.wewatch.data.model.Movie
import com.sample.wewatch.data.model.TmdbResponse
import io.reactivex.Observable

interface IMovieRepository {
    fun addMovie(movie: Movie)
    fun getAllMovies(): Observable<List<Movie>>
    fun deleteMovies(movies: List<Movie>)
    fun searchMovies(query: String): Observable<TmdbResponse>
}
