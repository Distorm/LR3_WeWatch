package com.sample.wewatch.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sample.wewatch.R
import com.sample.wewatch.model.Movie
import com.sample.wewatch.network.RetrofitClient
import com.squareup.picasso.Picasso

// Адаптер для списка фильмов в MainActivity
class MainAdapter(
  internal var movieList: List<Movie>,
  internal var context: Context
) : RecyclerView.Adapter<MainAdapter.MoviesHolder>() {

  val selectedMovies = HashSet<Movie>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {
    val v = LayoutInflater.from(context).inflate(R.layout.item_movie_main, parent, false)
    return MoviesHolder(v)
  }

  override fun onBindViewHolder(holder: MoviesHolder, position: Int) {
    holder.titleTextView.text = movieList[position].title
    holder.releaseDateTextView.text = movieList[position].releaseDate
    if (movieList[position].posterPath?.isEmpty() == true) {
      holder.movieImageView.setImageDrawable(context.getDrawable(R.drawable.ic_local_movies_gray))
    } else {
      Picasso.get().load(RetrofitClient.TMDB_IMAGEURL + movieList[position].posterPath).into(holder.movieImageView)
    }
  }

  override fun getItemCount(): Int {
    return movieList.size
  }

  inner class MoviesHolder(v: View) : RecyclerView.ViewHolder(v) {

    var titleTextView: TextView = v.findViewById(R.id.title_textview)
    var releaseDateTextView: TextView = v.findViewById(R.id.release_date_textview)
    var movieImageView: ImageView = v.findViewById(R.id.movie_imageview)
    var checkBox: CheckBox = v.findViewById(R.id.checkbox)

    init {
      checkBox.setOnClickListener {
        val adapterPosition = adapterPosition
        if (!selectedMovies.contains(movieList[adapterPosition])) {
          checkBox.isChecked = true
          selectedMovies.add(movieList[adapterPosition])
        } else {
          checkBox.isChecked = false
          selectedMovies.remove(movieList[adapterPosition])
        }
      }
    }
  }
}
