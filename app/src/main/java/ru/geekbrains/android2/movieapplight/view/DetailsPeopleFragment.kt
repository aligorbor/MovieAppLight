package ru.geekbrains.android2.movieapplight.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import ru.geekbrains.android2.movieapplight.R
import ru.geekbrains.android2.movieapplight.databinding.FragmentPeopleDetailsBinding
import ru.geekbrains.android2.movieapplight.model.Category
import ru.geekbrains.android2.movieapplight.model.Person
import ru.geekbrains.android2.movieapplight.utils.showSnackBar
import ru.geekbrains.android2.movieapplight.viewmodel.AppStateDetailsPeople
import ru.geekbrains.android2.movieapplight.viewmodel.DetailsPeopleViewModel

class DetailsPeopleFragment : Fragment() {
    private var _binding: FragmentPeopleDetailsBinding? = null
    private val binding get() = _binding!!
    private var people = Person()

    companion object {
        const val BUNDLE_EXTRA = "people"
        fun newInstance(bundle: Bundle): DetailsPeopleFragment {
            val fragment = DetailsPeopleFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var viewModel: DetailsPeopleViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPeopleDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Person>(BUNDLE_EXTRA)?.let { it ->
            people = it
        }

        viewModel = ViewModelProvider(this).get(DetailsPeopleViewModel::class.java).apply {
            getLiveData().observe(viewLifecycleOwner, {
                renderData(it)
            })
            getPeopleDetailFromRemoteSource(people)
        }

        binding.detailsPeoplePoster.setOnClickListener {
            openFragment(CategoryFragment.newInstance(Bundle().apply {
                putParcelable(
                    CategoryFragment.BUNDLE_EXTRA,
                    Category("", people.known_for, 0, people.isRus, false, 1, 1)
                )
            }))
        }
    }

    private fun renderData(appState: AppStateDetailsPeople) = with(binding) {
        when (appState) {
            is AppStateDetailsPeople.Success -> {
                detailsPeopleFragmentLoadingLayout.visibility = View.GONE
                detailsPeopleFragmentRootView.visibility = View.VISIBLE
                fillViews(appState.people)
            }
            is AppStateDetailsPeople.Loading -> {
                detailsPeopleFragmentLoadingLayout.visibility = View.VISIBLE
                detailsPeopleFragmentRootView.visibility = View.GONE
            }
            is AppStateDetailsPeople.Error -> {
                detailsPeopleFragmentLoadingLayout.visibility = View.GONE
                detailsPeopleFragmentRootView.visibility = View.VISIBLE
                detailsPeopleFragmentRootView.showSnackBar(
                    appState.error.message ?: "",
                    getString(R.string.reload),
                    {
                        viewModel.getPeopleDetailFromRemoteSource(people)
                    })
            }
        }
    }

    private fun fillViews(person: Person) {
        people = person
        with(person) {
            with(binding) {
                detailsPeopleName.text = name
                detailsPeopleGender.text =
                    String.format(
                        getString(R.string.people_gender),
                        gender
                    )
                detailsPeoplePopularity.text =
                    String.format(
                        getString(R.string.people_popularity),
                        popularity
                    )
                detailsPeopleBirthday.text =
                    String.format(
                        getString(R.string.people_birthday),
                        birthday
                    )
                detailsPeopleDeathday.text =
                    String.format(
                        getString(R.string.people_deathday),
                        deathday
                    )
                if (deathday == "") detailsPeopleDeathday.visibility = View.GONE
                else
                    detailsPeopleDeathday.visibility = View.VISIBLE
                detailsPeoplePlaceOfBirth.text = String.format(
                    getString(R.string.people_place_of_birth),
                    place_of_birth
                )
                detailsPeopleBiography.text = biography
                Picasso
                    .get()
                    .load(profile_path)
                    .into(detailsPeoplePoster)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun openFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.apply {
            beginTransaction()
                .add(R.id.container, fragment)
                .addToBackStack("")
                .commitAllowingStateLoss()
        }
    }
}