package com.example.watch.data

import com.example.watch.domain.MovieSearchRepository
import com.example.watch.models.RemoteDataSource
import com.example.watch.models.TmdbResponse
import io.reactivex.Observable

// Data Layer: Здесь находится реализация репозитория, которая взаимодействует с удаленным источником данных.
class RemoteMovieSearchRepository(private val dataSource: RemoteDataSource) :
    MovieSearchRepository {
    override fun searchMovies(query: String): Observable<TmdbResponse> {
        return dataSource.searchResultsObservable(query)
    }
}