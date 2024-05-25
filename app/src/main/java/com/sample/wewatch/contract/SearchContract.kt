package com.sample.wewatch.contract

import com.sample.wewatch.model.Movie

// Интерфейс контракта для SearchActivity
interface SearchContract {
    interface View {
        // Показать индикатор загрузки
        fun showLoading()
        // Скрыть индикатор загрузки
        fun hideLoading()
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
        // Выполнить поиск фильмов
        fun searchMovies(query: String)
        // Отключить представление
        fun detachView()
    }
}
