package com.sample.wewatch.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sample.wewatch.view.SearchActivity
import com.sample.wewatch.databinding.ItemMovieDetailsBinding
import com.sample.wewatch.model.Movie
import com.squareup.picasso.Picasso

// Адаптер для списка результатов поиска фильмов
class SearchAdapter(
  var movieList: List<Movie>,
  var context: Context,
  var listener: SearchActivity.RecyclerItemListener
) : RecyclerView.Adapter<SearchAdapter.SearchMoviesHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMoviesHolder {
    val binding = ItemMovieDetailsBinding.inflate(LayoutInflater.from(context), parent, false)
    return SearchMoviesHolder(binding)
  }

  override fun onBindViewHolder(holder: SearchMoviesHolder, position: Int) {
    val movie = movieList[position]
    holder.binding.titleTextview.text = movie.title
    holder.binding.releaseDateTextview.text = movie.getReleaseYearFromDate()
    holder.binding.overviewTextview.text = movie.overview

    if (movie.posterPath != "N/A") {
      Picasso.get().load(movie.posterPath).into(holder.binding.movieImageview)
    }

    holder.itemView.setOnClickListener {
      listener.onItemClick(it, position)
    }
  }

  override fun getItemCount(): Int {
    return movieList.size
  }

  fun getItemAtPosition(pos: Int): Movie {
    return movieList[pos]
  }

  inner class SearchMoviesHolder(val binding: ItemMovieDetailsBinding) : RecyclerView.ViewHolder(binding.root)
}
