package com.sample.wewatch.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sample.wewatch.contract.AddMovieContract
import com.sample.wewatch.databinding.ActivityAddMovieBinding
import com.sample.wewatch.model.LocalDataSource
import com.sample.wewatch.presenter.AddMoviePresenter
import com.squareup.picasso.Picasso

// Активити для добавления фильма
open class AddMovieActivity : AppCompatActivity(), AddMovieContract.View {

  private lateinit var binding: ActivityAddMovieBinding
  private lateinit var presenter: AddMovieContract.Presenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // Инициализация View Binding
    binding = ActivityAddMovieBinding.inflate(layoutInflater)
    setContentView(binding.root)

    setupViews()

    // Создание презентера
    AddMoviePresenter(this, LocalDataSource(application))
  }

  private fun setupViews() {
    // Назначение обработчиков событий для кнопок
    binding.searchButton.setOnClickListener { goToSearchMovieActivity() }
    binding.addMovieButton.setOnClickListener { onClickAddMovie() }
  }

  private fun goToSearchMovieActivity() {
    val title = binding.movieTitle.text.toString()
    val intent = Intent(this@AddMovieActivity, SearchActivity::class.java)
    intent.putExtra(SearchActivity.SEARCH_QUERY, title)
    startActivityForResult(intent, SEARCH_MOVIE_ACTIVITY_REQUEST_CODE)
  }

  private fun onClickAddMovie() {
    val title = binding.movieTitle.text.toString()
    val releaseDate = binding.movieReleaseDate.text.toString()
    val posterPath = if (binding.movieImageview.tag != null) binding.movieImageview.tag.toString() else ""

    // Вызов метода добавления фильма в презентере
    presenter.addMovie(title, releaseDate, posterPath)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    this@AddMovieActivity.runOnUiThread {
      binding.movieTitle.setText(data?.getStringExtra(SearchActivity.EXTRA_TITLE))
      binding.movieReleaseDate.setText(data?.getStringExtra(SearchActivity.EXTRA_RELEASE_DATE))
      binding.movieImageview.tag = data?.getStringExtra(SearchActivity.EXTRA_POSTER_PATH)
      Picasso.get().load(data?.getStringExtra(SearchActivity.EXTRA_POSTER_PATH)).into(binding.movieImageview)
    }
  }

  // Отображение сообщения об успешном добавлении фильма
  override fun showMovieAdded() {
    Toast.makeText(this, "Фильм успешно добавлен.", Toast.LENGTH_LONG).show()
    setResult(Activity.RESULT_OK)
    finish()
  }

  // Отображение сообщения об ошибке
  override fun showError(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
  }

  // Установка презентера
  override fun setPresenter(presenter: AddMovieContract.Presenter) {
    this.presenter = presenter
  }

  companion object {
    const val SEARCH_MOVIE_ACTIVITY_REQUEST_CODE = 2
  }
}
