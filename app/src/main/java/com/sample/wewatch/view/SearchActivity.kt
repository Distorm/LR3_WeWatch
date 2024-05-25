package com.sample.wewatch.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sample.wewatch.adapter.SearchAdapter
import com.sample.wewatch.contract.SearchContract
import com.sample.wewatch.databinding.ActivitySearchMovieBinding
import com.sample.wewatch.model.Movie
import com.sample.wewatch.model.RemoteDataSource
import com.sample.wewatch.presenter.SearchPresenter

// Активити для поиска фильмов
class SearchActivity : AppCompatActivity(), SearchContract.View {

  private val TAG = "SearchActivity"
  private lateinit var binding: ActivitySearchMovieBinding
  private lateinit var presenter: SearchContract.Presenter
  private lateinit var adapter: SearchAdapter
  private var query = ""

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // Инициализация View Binding
    binding = ActivitySearchMovieBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val i = intent
    query = i.getStringExtra(SEARCH_QUERY) ?: ""
    setupViews()

    // Создание презентера
    SearchPresenter(this, RemoteDataSource())
  }

  override fun onStart() {
    super.onStart()
    presenter.searchMovies(query)
  }

  override fun onStop() {
    super.onStop()
    presenter.detachView()
  }

  private fun setupViews() {
    binding.searchResultsRecyclerview.layoutManager = LinearLayoutManager(this)
  }

  // Показать индикатор загрузки
  override fun showLoading() {
    binding.progressBar.visibility = View.VISIBLE
  }

  // Скрыть индикатор загрузки
  override fun hideLoading() {
    binding.progressBar.visibility = View.INVISIBLE
  }

  // Показать фильмы
  override fun showMovies(movies: List<Movie>) {
    binding.searchResultsRecyclerview.visibility = View.VISIBLE
    binding.noMoviesTextview.visibility = View.INVISIBLE
    adapter = SearchAdapter(movies, this, itemListener)
    binding.searchResultsRecyclerview.adapter = adapter
  }

  // Показать сообщение, что фильмов нет
  override fun showNoMovies() {
    binding.searchResultsRecyclerview.visibility = View.INVISIBLE
    binding.noMoviesTextview.visibility = View.VISIBLE
  }

  // Показать сообщение об ошибке
  override fun showError(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
  }

  // Установить презентера
  override fun setPresenter(presenter: SearchContract.Presenter) {
    this.presenter = presenter
  }

  companion object {
    const val SEARCH_QUERY = "searchQuery"
    const val EXTRA_TITLE = "SearchActivity.TITLE_REPLY"
    const val EXTRA_RELEASE_DATE = "SearchActivity.RELEASE_DATE_REPLY"
    const val EXTRA_POSTER_PATH = "SearchActivity.POSTER_PATH_REPLY"
  }

  internal var itemListener: RecyclerItemListener = object : RecyclerItemListener {
    override fun onItemClick(view: View, position: Int) {
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
