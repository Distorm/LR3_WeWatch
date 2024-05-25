package com.sample.wewatch.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sample.wewatch.R
import com.sample.wewatch.contract.MainContract
import com.sample.wewatch.databinding.ActivityMainBinding
import com.sample.wewatch.model.LocalDataSource
import com.sample.wewatch.model.Movie
import com.sample.wewatch.presenter.MainPresenter
import com.sample.wewatch.adapter.MainAdapter

// Главная активити
class MainActivity : AppCompatActivity(), MainContract.View {

  private lateinit var binding: ActivityMainBinding
  private lateinit var presenter: MainContract.Presenter
  private var adapter: MainAdapter? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // Инициализация View Binding
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    setupViews()

    // Создание презентера
    MainPresenter(this, LocalDataSource(application))
  }

  override fun onStart() {
    super.onStart()
    presenter.loadMovies()
  }

  override fun onStop() {
    super.onStop()
    presenter.detachView()
  }

  private fun setupViews() {
    binding.moviesRecyclerview.layoutManager = LinearLayoutManager(this)
    binding.fab.setOnClickListener { goToAddMovieActivity() }
  }

  private fun goToAddMovieActivity() {
    val myIntent = Intent(this@MainActivity, AddMovieActivity::class.java)
    startActivityForResult(myIntent, ADD_MOVIE_ACTIVITY_REQUEST_CODE)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == ADD_MOVIE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
      showToast("Фильм успешно добавлен.")
      presenter.loadMovies()
    } else {
      showToast("Не удалось добавить фильм.")
    }
  }

  // Показать фильмы
  override fun showMovies(movies: List<Movie>) {
    adapter = MainAdapter(movies, this)
    binding.moviesRecyclerview.adapter = adapter
    binding.moviesRecyclerview.visibility = View.VISIBLE
    binding.noMoviesLayout.visibility = View.INVISIBLE
  }

  // Показать сообщение, что фильмов нет
  override fun showNoMovies() {
    binding.moviesRecyclerview.visibility = View.INVISIBLE
    binding.noMoviesLayout.visibility = View.VISIBLE
  }

  // Показать сообщение об ошибке
  override fun showError(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
  }

  // Установить презентера
  override fun setPresenter(presenter: MainContract.Presenter) {
    this.presenter = presenter
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.deleteMenuItem -> {
        val selectedMovies = adapter?.selectedMovies ?: emptyList()
        if (selectedMovies.isNotEmpty()) {
          presenter.deleteSelectedMovies(selectedMovies)
          showToast(if (selectedMovies.size == 1) "Фильм удален" else "Фильмы удалены")
        }
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  fun showToast(str: String) {
    Toast.makeText(this@MainActivity, str, Toast.LENGTH_LONG).show()
  }

  companion object {
    const val ADD_MOVIE_ACTIVITY_REQUEST_CODE = 1
  }
}
