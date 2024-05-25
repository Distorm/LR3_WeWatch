package com.sample.wewatch.presenter

import com.sample.wewatch.contract.AddMovieContract
import com.sample.wewatch.model.LocalDataSource
import com.sample.wewatch.model.Movie

// Презентер для AddMovieActivity
class AddMoviePresenter(
    private val view: AddMovieContract.View,
    private val dataSource: LocalDataSource
) : AddMovieContract.Presenter {

    init {
        view.setPresenter(this)
    }

    override fun addMovie(title: String, releaseDate: String, posterPath: String) {
        if (title.isEmpty()) {
            view.showError("Название фильма не может быть пустым")
            return
        }

        val movie = Movie(title = title, releaseDate = releaseDate, posterPath = posterPath)
        dataSource.insert(movie)
        view.showMovieAdded()
    }

    override fun detachView() {
        // No-op
    }
}
