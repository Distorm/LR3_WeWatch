package com.example.watch.domain

import com.example.watch.models.Movie

// Domain Layer: Здесь находится интерфейс репозитория и бизнес-логика (use case).
interface AddMovieRepository {
    fun insertMovie(movie: Movie)
}

class AddMovieUseCase(private val repository: AddMovieRepository) {
    fun execute(movie: Movie) {
        repository.insertMovie(movie)
    }
}