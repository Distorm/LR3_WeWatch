package com.example.watch.data

import com.example.watch.domain.MovieRepository
import com.example.watch.models.LocalDataSource
import com.example.watch.models.Movie
import io.reactivex.Observable

// Data Layer: Здесь находится реализация репозитория, которая взаимодействует с локальным источником данных.
class LocalMovieRepository(private val dataSource: LocalDataSource) : MovieRepository {
    override fun getAllMovies(): Observable<List<Movie>> = dataSource.allMovies
    override fun deleteMovie(movie: Movie) = dataSource.delete(movie)
}