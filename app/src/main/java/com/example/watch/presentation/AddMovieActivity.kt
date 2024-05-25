package com.example.watch.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.watch.R
import com.example.watch.data.LocalAddMovieRepository
import com.example.watch.domain.AddMovieRepository
import com.example.watch.domain.AddMovieUseCase
import com.example.watch.models.LocalDataSource
import com.example.watch.models.Movie
import com.example.watch.network.RetrofitClient.TMDB_IMAGEURL
import com.squareup.picasso.Picasso

// Presentation Layer: Здесь находится AddMovieActivity, которая теперь использует use case для добавления фильма.
class AddMovieActivity : AppCompatActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var releaseDateEditText: EditText
    private lateinit var movieImageView: ImageView
    private lateinit var movieRepository: AddMovieRepository
    private lateinit var addMovieUseCase: AddMovieUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movie)
        setupViews()
        initializeUseCases()
    }

    private fun setupViews() {
        titleEditText = findViewById(R.id.movie_title)
        releaseDateEditText = findViewById(R.id.movie_release_date)
        movieImageView = findViewById(R.id.movie_imageview)
    }

    private fun initializeUseCases() {
        val dataSource = LocalDataSource(application)
        movieRepository = LocalAddMovieRepository(dataSource)
        addMovieUseCase = AddMovieUseCase(movieRepository)
    }

    @Suppress("DEPRECATION")
    fun goToSearchMovieActivity(v: View) {
        val title = titleEditText.text.toString()
        val intent = Intent(this@AddMovieActivity, SearchActivity::class.java)
        intent.putExtra(SearchActivity.SEARCH_QUERY, title)
        startActivityForResult(intent, SEARCH_MOVIE_ACTIVITY_REQUEST_CODE)
    }

    fun onClickAddMovie(v: View) {
        if (TextUtils.isEmpty(titleEditText.text)) {
            showToast()
        } else {
            val title = titleEditText.text.toString()
            val releaseDate = releaseDateEditText.text.toString()
            val posterPath = if (movieImageView.tag != null) movieImageView.tag.toString() else ""

            val movie = Movie(title = title, releaseDate = releaseDate, posterPath = posterPath)
            addMovieUseCase.execute(movie)

            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        this@AddMovieActivity.runOnUiThread {
            titleEditText.setText(data?.getStringExtra(SearchActivity.EXTRA_TITLE))
            releaseDateEditText.setText(data?.getStringExtra(SearchActivity.EXTRA_RELEASE_DATE))
            movieImageView.tag = data?.getStringExtra(SearchActivity.EXTRA_POSTER_PATH)
            Picasso.get().load(TMDB_IMAGEURL + data?.getStringExtra(SearchActivity.EXTRA_POSTER_PATH)).into(movieImageView)
        }
    }

    private fun showToast() {
        Toast.makeText(this@AddMovieActivity, "Movie title can't be empty", Toast.LENGTH_LONG).show()
    }

    companion object {
        const val SEARCH_MOVIE_ACTIVITY_REQUEST_CODE = 2
    }
}