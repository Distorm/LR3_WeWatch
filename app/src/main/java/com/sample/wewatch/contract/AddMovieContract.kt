package com.sample.wewatch.contract


// Интерфейс контракта для AddMovieActivity
interface AddMovieContract {
    interface View {
        // Показать сообщение об успешном добавлении фильма
        fun showMovieAdded()
        // Показать сообщение об ошибке
        fun showError(message: String)
        // Установить презентер
        fun setPresenter(presenter: Presenter)
    }

    interface Presenter {
        // Добавить фильм
        fun addMovie(title: String, releaseDate: String, posterPath: String)
        // Отключить представление
        fun detachView()
    }
}