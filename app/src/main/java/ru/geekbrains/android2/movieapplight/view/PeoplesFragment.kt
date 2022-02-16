package ru.geekbrains.android2.movieapplight.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.geekbrains.android2.movieapplight.R
import ru.geekbrains.android2.movieapplight.databinding.FragmentPeoplesBinding
import ru.geekbrains.android2.movieapplight.model.Person
import ru.geekbrains.android2.movieapplight.model.Persons
import ru.geekbrains.android2.movieapplight.utils.showSnackBar
import ru.geekbrains.android2.movieapplight.viewmodel.AppStatePeoples
import ru.geekbrains.android2.movieapplight.viewmodel.PeoplesViewModel

class PeoplesFragment : Fragment() {
    private var _binding: FragmentPeoplesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PeoplesViewModel

    private val onItemViewClickListener = object : OnItemViewClickListener {
        override fun onItemViewClick(person: Person) {
            activity?.supportFragmentManager?.apply {
                beginTransaction()
                    .add(R.id.container, DetailsPeopleFragment.newInstance(Bundle().apply {
                        person.isRus = isDataSetRus
                        putParcelable(DetailsPeopleFragment.BUNDLE_EXTRA, person)
                    }))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    }

    private val onNewPage = object : OnNewPage {
        override fun getPage(isRus: Boolean, adult: Boolean, page: Int) {
            viewModel.getPeoplesFromRemoteSource(
                isDataSetRus,
                adult,
                page
            )
        }
    }

    private var peoples = Persons()

    private val adapter =
        PeoplesFragmentAdapter(onItemViewClickListener, onNewPage)
    private var isDataSetRus: Boolean = true
    private var adult: Boolean = false

    companion object {
        const val BUNDLE_IS_RUS = "is_rus"
        const val BUNDLE_ADULT = "adult"
        fun newInstance(bundle: Bundle): PeoplesFragment {
            val fragment = PeoplesFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPeoplesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.getBoolean(BUNDLE_IS_RUS, true)?.let { isDataSetRus = it }
        arguments?.getBoolean(BUNDLE_ADULT, false)?.let { adult = it }

        binding.peoplesFragmentRecyclerView.adapter = adapter
        adapter.setPeople(peoples)

        viewModel = ViewModelProvider(this).get(PeoplesViewModel::class.java).apply {
            getLiveData().observe(viewLifecycleOwner, {
                renderData(it)
            })
            getPeoplesFromRemoteSource(
                isDataSetRus,
                adult,
                1
            )
        }
    }

    private fun renderData(appState: AppStatePeoples) = with(binding) {
        when (appState) {
            is AppStatePeoples.Success -> {
                peoplesFragmentLoadingLayout.visibility = View.GONE
                adapter.setPeople(appState.peoplesData)
            }

            is AppStatePeoples.Loading -> {
                peoplesFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppStatePeoples.Error -> {
                peoplesFragmentLoadingLayout.visibility = View.GONE
                peoplesFragmentRootView.showSnackBar(
                    appState.error.message ?: "",
                    getString(R.string.reload),
                    {
                        viewModel.getPeoplesFromRemoteSource(
                            isDataSetRus,
                            adult,
                            1
                        )
                    })
            }
        }
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(person: Person)
    }

    interface OnNewPage {
        fun getPage(
            isRus: Boolean,
            adult: Boolean,
            page: Int
        )
    }
}