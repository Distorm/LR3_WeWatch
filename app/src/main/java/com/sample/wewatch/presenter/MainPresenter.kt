package com.sample.wewatch.presenter

import com.sample.wewatch.contract.MainContract
import com.sample.wewatch.model.LocalDataSource
import com.sample.wewatch.model.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

// Презентер для MainActivity
class MainPresenter(
    private val view: MainContract.View,
    private val dataSource: LocalDataSource
) : MainContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    init {
        view.setPresenter(this)
    }

    override fun loadMovies() {
        val myMoviesDisposable = dataSource.allMovies
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<Movie>>() {
                override fun onNext(movieList: List<Movie>) {
                    if (movieList.isEmpty()) {
                        view.showNoMovies()
                    } else {
                        view.showMovies(movieList)
                    }
                }

                override fun onError(e: Throwable) {
                    view.showError("Ошибка при загрузке фильмов")
                }

                override fun onComplete() {
                    // No-op
                }
            })

        compositeDisposable.add(myMoviesDisposable)
    }

    override fun deleteSelectedMovies(movies: Collection<Movie>) {
        movies.forEach { dataSource.delete(it) }
        loadMovies()
    }

    override fun detachView() {
        compositeDisposable.clear()
    }
}
