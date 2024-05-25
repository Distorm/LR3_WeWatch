package com.sample.wewatch.data.repository

import com.sample.wewatch.data.local.LocalDataSource
import com.sample.wewatch.data.remote.RemoteDataSource
import com.sample.wewatch.data.model.Movie
import com.sample.wewatch.data.model.TmdbResponse
import com.sample.wewatch.domain.repository.IMovieRepository
import io.reactivex.Observable

class MovieRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : IMovieRepository {

    override fun addMovie(movie: Movie) {
        localDataSource.insert(movie)
    }

    override fun getAllMovies(): Observable<List<Movie>> {
        return localDataSource.getAllMovies()
    }

    override fun deleteMovies(movies: List<Movie>) {
        movies.forEach { localDataSource.delete(it) }
    }

    override fun searchMovies(query: String): Observable<TmdbResponse> {
        return remoteDataSource.searchMovies(query)
    }
}
