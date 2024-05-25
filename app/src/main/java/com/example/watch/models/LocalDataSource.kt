package com.example.watch.models

import android.app.Application
import io.reactivex.Observable
import kotlin.concurrent.thread

@Suppress("unused")
class LocalDataSource(application: Application) {
    private val movieDao: MovieDao
    val allMovies: Observable<List<Movie>>

    init {
        val db = LocalDatabase.getInstance(application)
        movieDao = db.movieDao()
        allMovies = movieDao.all
    }

    fun insert(movie: Movie) {
        thread {
            movieDao.insert(movie)
        }
    }

    fun delete(movie: Movie) {
        thread {
            movieDao.delete(movie.id)
        }
    }

    fun update(movie: Movie) {
        thread {
            movieDao.update(movie)
        }
    }
}