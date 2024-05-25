package com.example.watch.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.watch.R
import com.example.watch.models.Movie
import com.example.watch.network.RetrofitClient

import com.squareup.picasso.Picasso

import java.util.HashSet

class MainAdapter(internal var movieList: List<Movie>, private var context: Context) : RecyclerView.Adapter<MainAdapter.MoviesHolder>() {
    val selectedMovies = HashSet<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_movie_main, parent, false)
        return MoviesHolder(v)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: MoviesHolder, position: Int) {
        holder.titleTextView.text = movieList[position].title
        holder.releaseDateTextView.text = movieList[position].releaseDate
        if (movieList[position].posterPath.equals("")) {
            holder.movieImageView.setImageDrawable(context.getDrawable(R.drawable.ic_local_movies_gray))
        } else {
            Picasso.get().load(RetrofitClient.TMDB_IMAGEURL + movieList[position].posterPath).into(holder.movieImageView)
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class MoviesHolder(v: View) : RecyclerView.ViewHolder(v) {

        internal var titleTextView: TextView
        internal var releaseDateTextView: TextView
        internal var movieImageView: ImageView
        private var checkBox: CheckBox

        init {
            titleTextView = v.findViewById(R.id.title_textview)
            releaseDateTextView = v.findViewById(R.id.release_date_textview)
            movieImageView = v.findViewById(R.id.movie_imageview)
            checkBox = v.findViewById(R.id.checkbox)
            checkBox.setOnClickListener {
                val adapterPosition = adapterPosition
                if (!selectedMovies.contains(movieList[adapterPosition])) {
                    checkBox.isChecked = true
                    selectedMovies.add(movieList[adapterPosition])
                } else {
                    checkBox.isChecked = false
                    selectedMovies.add(movieList[adapterPosition])
                }
            }
        }
    }
}