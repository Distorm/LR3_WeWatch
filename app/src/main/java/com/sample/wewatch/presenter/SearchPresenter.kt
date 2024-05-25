package com.sample.wewatch.presenter

import com.sample.wewatch.contract.SearchContract
import com.sample.wewatch.model.RemoteDataSource
import com.sample.wewatch.model.TmdbResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

// Презентер для SearchActivity
class SearchPresenter(
    private val view: SearchContract.View,
    private val dataSource: RemoteDataSource
) : SearchContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    init {
        view.setPresenter(this)
    }

    override fun searchMovies(query: String) {
        view.showLoading()
        val searchResultsDisposable = searchResultsObservable(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<TmdbResponse>() {

                override fun onNext(tmdbResponse: TmdbResponse) {
                    if (tmdbResponse.totalResults == null || tmdbResponse.totalResults == 0) {
                        view.showNoMovies()
                    } else {
                        view.showMovies(tmdbResponse.results ?: arrayListOf())
                    }
                }

                override fun onError(e: Throwable) {
                    view.showError("Ошибка при получении данных о фильмах")
                }

                override fun onComplete() {
                    view.hideLoading()
                }
            })

        compositeDisposable.add(searchResultsDisposable)
    }

    private fun searchResultsObservable(query: String): Observable<TmdbResponse> {
        return dataSource.searchResultsObservable(query)
    }

    override fun detachView() {
        compositeDisposable.clear()
    }
}
