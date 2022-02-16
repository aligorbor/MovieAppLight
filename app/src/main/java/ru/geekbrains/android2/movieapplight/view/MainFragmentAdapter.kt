package ru.geekbrains.android2.movieapplight.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.geekbrains.android2.movieapplight.R
import ru.geekbrains.android2.movieapplight.model.Category
import ru.geekbrains.android2.movieapplight.model.Movie

class MainFragmentAdapter(
    private var onItemViewClickListener: MainFragment.OnItemViewClickListener,
    private var setFavoriteToMovie: MainFragment.SetFavoriteToMovie,
    private var setSameMovies: MainFragmentCategoryAdapter.SetSameMovies,
    private var onNewPage: MainFragment.OnNewPage
) :
    RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

    private var category: Category = Category()
    private var movieList = mutableListOf<Movie>()

    fun setMovie(data: Category) {
        val firstItemPosition = movieList.size
        movieList.addAll(data.movies)
        category = data
        notifyItemRangeInserted(firstItemPosition, category.movies.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_main_recycler_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        if (position == movieList.size - 1 && category.page < category.total_pages) {
            onNewPage.getPage(category.isRus, category.adult, category.page + 1, category.id)
        }
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie) {
            itemView.apply {
                with(movie) {
                    findViewById<TextView>(R.id.item_title).text = title
                    findViewById<TextView>(R.id.item_release_date).text = release_date
                    findViewById<TextView>(R.id.item_vote_average).text =
                        vote_average.toString()
                    setOnClickListener {
                        onItemViewClickListener?.onItemViewClick(movie)
                    }
                    Picasso
                        .get()
                        .load(poster_path)
                        .into(findViewById<ImageView>(R.id.poster))

                    val imageFavorite = findViewById<ImageView>(R.id.favorite)
                    if (movie.isFavorite)
                        imageFavorite.setImageResource(R.drawable.heart_red)
                    else
                        imageFavorite.setImageResource(R.drawable.heart_line)

                    imageFavorite.setOnClickListener {
                        movie.isFavorite = !movie.isFavorite
                        if (movie.isFavorite)
                            imageFavorite.setImageResource(R.drawable.heart_red)
                        else
                            imageFavorite.setImageResource(R.drawable.heart_line)
                        setSameMovies.setSameMoviesFavorite(movie, category.id)
                        setFavoriteToMovie.setFavorite(movie)
                    }
                }
            }
        }
    }
}