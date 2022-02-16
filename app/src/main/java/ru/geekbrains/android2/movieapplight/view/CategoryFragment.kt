package ru.geekbrains.android2.movieapplight.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.geekbrains.android2.movieapplight.R
import ru.geekbrains.android2.movieapplight.databinding.FragmentCategoryBinding
import ru.geekbrains.android2.movieapplight.interactors.StringsInteractorImpl
import ru.geekbrains.android2.movieapplight.model.Category
import ru.geekbrains.android2.movieapplight.model.Movie
import ru.geekbrains.android2.movieapplight.utils.showSnackBar
import ru.geekbrains.android2.movieapplight.viewmodel.AppState
import ru.geekbrains.android2.movieapplight.viewmodel.CategoryViewModel

class CategoryFragment : Fragment() {
    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CategoryViewModel

    private val onItemViewClickListener = object : OnItemViewClickListener {
        override fun onItemViewClick(movie: Movie) {
            activity?.supportFragmentManager?.apply {
                beginTransaction()
                    .add(R.id.container, DetailsFragment.newInstance(Bundle().apply {
                        movie.isRus = category.isRus
                        putParcelable(DetailsFragment.BUNDLE_EXTRA, movie)
                    }))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    }

    private val onNewPage = object : OnNewPage {
        override fun getPage(isRus: Boolean, adult: Boolean, page: Int, id: Int) {
            viewModel.getCategoryByIdFromRemoteSource(
                isRus,
                StringsInteractorImpl(requireContext()),
                adult,
                page,
                id
            )
        }
    }

    private var category = Category()

    private val adapter =
        CategoryFragmentAdapter(onItemViewClickListener, onNewPage)

    companion object {
        const val BUNDLE_EXTRA = "category"
        fun newInstance(bundle: Bundle): CategoryFragment {
            val fragment = CategoryFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.getParcelable<Category>(BUNDLE_EXTRA)?.let {
            category = it
        }
        binding.categoryFragmentRecyclerView.adapter = adapter
        adapter.setMovie(category)
        viewModel = ViewModelProvider(this).get(CategoryViewModel::class.java).apply {
            getLiveData().observe(viewLifecycleOwner, {
                renderData(it)
            })
        }
    }

    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.SuccessCategoryById -> {
                categoryFragmentLoadingLayout.visibility = View.GONE
                adapter.setMovie(appState.category)
            }
            is AppState.Loading -> {
                categoryFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                categoryFragmentLoadingLayout.visibility = View.GONE
                categoryFragmentRootView.showSnackBar(
                    appState.error.message ?: "",
                    getString(R.string.reload),
                    {
                        viewModel.getCategoryByIdFromRemoteSource(
                            category.isRus,
                            StringsInteractorImpl(requireContext()),
                            category.adult,
                            category.page,
                            category.id
                        )
                    })
            }
        }
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(movie: Movie)
    }

    interface OnNewPage {
        fun getPage(
            isRus: Boolean,
            adult: Boolean,
            page: Int,
            id: Int
        )
    }
}