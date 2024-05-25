package com.example.watch.domain

import com.example.watch.models.Movie
import io.reactivex.Observable

// Domain Layer: Здесь находятся интерфейсы репозиториев и бизнес-логика (use cases).
interface MovieRepository {
    fun getAllMovies(): Observable<List<Movie>>
    fun deleteMovie(movie: Movie)
}

class GetMoviesUseCase(private val repository: MovieRepository) {
    fun execute(): Observable<List<Movie>> {
        return repository.getAllMovies()
    }
}

class DeleteMoviesUseCase(private val repository: MovieRepository) {
    fun execute(movies: HashSet<Movie>) {
        for (movie in movies) {
            repository.deleteMovie(movie)
        }
    }
}