package ru.geekbrains.android2.movieapplight.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrains.android2.movieapplight.R
import ru.geekbrains.android2.movieapplight.model.Category
import ru.geekbrains.android2.movieapplight.model.Movie

class MainFragmentCategoryAdapter(
    private var onItemViewClickListener: MainFragment.OnItemViewClickListener,
    private var onCategoryClickListener: MainFragment.OnCategoryClickListener,
    private var setFavoriteToMovie: MainFragment.SetFavoriteToMovie,
    private var onNewPage: MainFragment.OnNewPage
) :
    RecyclerView.Adapter<MainFragmentCategoryAdapter.MainViewHolder>() {

    private var catgoryData: List<Category> = listOf()
    private var adapters = mutableListOf<MainFragmentAdapter>()

    fun setCategory(data: List<Category>) {
        catgoryData = data
        for (category in data) {
            adapters.add(MainFragmentAdapter(
                onItemViewClickListener,
                setFavoriteToMovie,
                setSameMovies,
                onNewPage
            )
                .also {
                    it.setMovie(category)
                })
        }
        notifyDataSetChanged()
    }

    fun setCategoryById(data: Category) {
        adapters[data.id].setMovie(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_main_recycler_category, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(catgoryData[position], position)
    }

    override fun getItemCount(): Int {
        return catgoryData.size
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(category: Category, position: Int) {
            itemView.apply {
                findViewById<TextView>(R.id.textCategory).apply {
                    text = category.name
                    setOnClickListener {
                        onCategoryClickListener?.onCategoryClick(category)
                    }
                }
                findViewById<RecyclerView>(R.id.mainFragmentRecyclerView).adapter =
                    adapters[position]
            }
        }
    }

    private val setSameMovies = object : SetSameMovies {
        override fun setSameMoviesFavorite(movieToSet: Movie, catgoryID: Int) {
            for (category in catgoryData) {
                for (movie in category.movies) {
                    if (movie.id == movieToSet.id && category.id != catgoryID) {
                        movie.isFavorite = movieToSet.isFavorite
                        notifyItemChanged(category.id)
                    }
                }
            }
        }
    }

    interface SetSameMovies {
        fun setSameMoviesFavorite(movieToSet: Movie, catgoryID: Int)
    }
}