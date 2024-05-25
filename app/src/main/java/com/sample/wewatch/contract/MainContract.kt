package com.sample.wewatch.contract

import com.sample.wewatch.model.Movie

// Интерфейс контракта для MainActivity
interface MainContract {
    interface View {
        // Показать список фильмов
        fun showMovies(movies: List<Movie>)
        // Показать сообщение о том, что фильмов нет
        fun showNoMovies()
        // Показать сообщение об ошибке
        fun showError(message: String)
        // Установить презентер
        fun setPresenter(presenter: Presenter)
    }

    interface Presenter {
        // Загрузить фильмы
        fun loadMovies()
        // Удалить выбранные фильмы
        fun deleteSelectedMovies(movies: Collection<Movie>)
        // Отключить представление
        fun detachView()
    }
}