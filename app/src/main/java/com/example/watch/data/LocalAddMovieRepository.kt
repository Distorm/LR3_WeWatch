package com.example.watch.data

import com.example.watch.domain.AddMovieRepository
import com.example.watch.models.LocalDataSource
import com.example.watch.models.Movie

// Data Layer: Здесь находится реализация репозитория, которая взаимодействует с локальным источником данных 1111.
class LocalAddMovieRepository(private val dataSource: LocalDataSource) : AddMovieRepository {
    override fun insertMovie(movie: Movie) = dataSource.insert(movie)
}