package ru.geekbrains.android2.movieapplight.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import ru.geekbrains.android2.movieapplight.R
import ru.geekbrains.android2.movieapplight.databinding.FragmentDetailsBinding
import ru.geekbrains.android2.movieapplight.model.Movie
import ru.geekbrains.android2.movieapplight.utils.showSnackBar
import ru.geekbrains.android2.movieapplight.viewmodel.AppStateDetails
import ru.geekbrains.android2.movieapplight.viewmodel.DetailsViewModel

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private var movie = Movie()

    companion object {
        const val BUNDLE_EXTRA = "movie"
        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var viewModel: DetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Movie>(BUNDLE_EXTRA)?.let { it ->
            movie = it
        }

        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java).apply {
            getLiveData().observe(viewLifecycleOwner, {
                renderData(it)
            })
            getMovieDetailFromRemoteSource(movie)
        }
    }

    private fun renderData(appState: AppStateDetails) = with(binding) {
        when (appState) {
            is AppStateDetails.Success -> {
                detailsFragmentLoadingLayout.visibility = View.GONE
                detailsFragmentRootView.visibility = View.VISIBLE
                fillViews(appState.movie)
            }
            is AppStateDetails.Loading -> {
                detailsFragmentLoadingLayout.visibility = View.VISIBLE
                detailsFragmentRootView.visibility = View.GONE
            }
            is AppStateDetails.Error -> {
                detailsFragmentLoadingLayout.visibility = View.GONE
                detailsFragmentRootView.visibility = View.VISIBLE
                detailsFragmentRootView.showSnackBar(
                    appState.error.message ?: "",
                    getString(R.string.reload),
                    {
                        viewModel.getMovieDetailFromRemoteSource(movie)
                    })
            }
        }
    }

    private fun fillViews(movie: Movie) {
        with(movie) {
            with(binding) {
                detailsTitle.text = title
                detailsVoteAverage.text =
                    String.format(
                        getString(R.string.rating),
                        vote_average,
                        vote_count
                    )
                detailsReleaseDate.text =
                    String.format(
                        getString(R.string.release_date),
                        release_date
                    )
                detailsGenres.text = genres
                detailsDuration.text =
                    String.format(
                        getString(R.string.duration),
                        runtime
                    )
                detailsBudget.text = String.format(
                    getString(R.string.budget),
                    budget
                )
                detailsRevenue.text = String.format(
                    getString(R.string.revenue),
                    revenue
                )
                detailsOverview.text = overview
                detailsOriginalTitle.text = original_title
                detailsNote.setText(movie.note)
                Picasso
                    .get()
                    .load(poster_path)
                    .into(detailsPoster)
                Picasso
                    .get()
                    .load(backdrop_path)
                    .into(detailsBackdrop)
            }
        }
    }

    override fun onPause() {
        movie.note = binding.detailsNote.text.toString()
        viewModel.saveMovieToHistory(movie)
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}