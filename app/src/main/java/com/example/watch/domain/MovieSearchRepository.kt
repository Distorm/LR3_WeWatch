package com.example.watch.domain

import com.example.watch.models.TmdbResponse
import io.reactivex.Observable

// Domain Layer: Здесь находится интерфейс репозитория и бизнес-логика (use case) для поиска фильмов.
interface MovieSearchRepository {
    fun searchMovies(query: String): Observable<TmdbResponse>
}

class SearchMoviesUseCase(private val repository: MovieSearchRepository) {
    fun execute(query: String): Observable<TmdbResponse> {
        return repository.searchMovies(query)
    }
}