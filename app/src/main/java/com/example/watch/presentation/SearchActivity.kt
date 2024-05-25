package com.example.watch.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.watch.R
import com.example.watch.adapters.SearchAdapter
import com.example.watch.data.RemoteMovieSearchRepository
import com.example.watch.domain.MovieSearchRepository
import com.example.watch.domain.SearchMoviesUseCase
import com.example.watch.models.RemoteDataSource
import com.example.watch.models.TmdbResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

// Presentation Layer: Здесь находится SearchActivity, которая теперь использует use case для поиска фильмов.
class SearchActivity : AppCompatActivity() {
    private val tag = "SearchActivity"
    private lateinit var searchResultsRecyclerView: RecyclerView
    private lateinit var adapter: SearchAdapter
    private lateinit var noMoviesTextView: TextView
    private lateinit var progressBar: ProgressBar
    private var query = ""

    private lateinit var movieSearchRepository: MovieSearchRepository
    private lateinit var searchMoviesUseCase: SearchMoviesUseCase
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_movie)
        searchResultsRecyclerView = findViewById(R.id.search_results_recyclerview)
        noMoviesTextView = findViewById(R.id.no_movies_textview)
        progressBar = findViewById(R.id.progress_bar)

        query = intent.getStringExtra(SEARCH_QUERY) ?: ""
        setupViews()
        initializeUseCases()
    }

    override fun onStart() {
        super.onStart()
        progressBar.visibility = VISIBLE
        getSearchResults(query)
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    private fun setupViews() {
        searchResultsRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun initializeUseCases() {
        val dataSource = RemoteDataSource()
        movieSearchRepository = RemoteMovieSearchRepository(dataSource)
        searchMoviesUseCase = SearchMoviesUseCase(movieSearchRepository)
    }

    private fun getSearchResults(query: String) {
        val searchResultsDisposable = searchMoviesUseCase.execute(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(observer)

        compositeDisposable.add(searchResultsDisposable)
    }

    private val observer: DisposableObserver<TmdbResponse>
        get() = object : DisposableObserver<TmdbResponse>() {
            override fun onNext(tmdbResponse: TmdbResponse) {
                Log.d(tag, "OnNext" + tmdbResponse.totalResults)
                displayResult(tmdbResponse)
            }

            override fun onError(e: Throwable) {
                Log.d(tag, "Error$e")
                e.printStackTrace()
                displayError("Error fetching Movie Data")
            }

            override fun onComplete() {
                Log.d(tag, "Completed")
            }
        }

    private fun displayResult(tmdbResponse: TmdbResponse) {
        progressBar.visibility = INVISIBLE

        if (tmdbResponse.totalResults == null || tmdbResponse.totalResults == 0) {
            searchResultsRecyclerView.visibility = INVISIBLE
            noMoviesTextView.visibility = VISIBLE
        } else {
            adapter = SearchAdapter(tmdbResponse.results ?: arrayListOf(), this@SearchActivity, itemListener)
            searchResultsRecyclerView.adapter = adapter

            searchResultsRecyclerView.visibility = VISIBLE
            noMoviesTextView.visibility = INVISIBLE
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@SearchActivity, message, Toast.LENGTH_LONG).show()
    }

    private fun displayError(message: String) {
        showToast(message)
    }

    companion object {
        const val SEARCH_QUERY = "searchQuery"
        const val EXTRA_TITLE = "SearchActivity.TITLE_REPLY"
        const val EXTRA_RELEASE_DATE = "SearchActivity.RELEASE_DATE_REPLY"
        const val EXTRA_POSTER_PATH = "SearchActivity.POSTER_PATH_REPLY"
    }

    private val itemListener: RecyclerItemListener = object : RecyclerItemListener {
        override fun onItemClick(v: View, position: Int) {
            val movie = adapter.getItemAtPosition(position)

            val replyIntent = Intent()
            replyIntent.putExtra(EXTRA_TITLE, movie.title)
            replyIntent.putExtra(EXTRA_RELEASE_DATE, movie.getReleaseYearFromDate())
            replyIntent.putExtra(EXTRA_POSTER_PATH, movie.posterPath)
            setResult(Activity.RESULT_OK, replyIntent)

            finish()
        }
    }

    interface RecyclerItemListener {
        fun onItemClick(v: View, position: Int)
    }
}