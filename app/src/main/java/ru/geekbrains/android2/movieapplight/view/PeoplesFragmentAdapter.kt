package ru.geekbrains.android2.movieapplight.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.geekbrains.android2.movieapplight.R
import ru.geekbrains.android2.movieapplight.model.Person
import ru.geekbrains.android2.movieapplight.model.Persons

class PeoplesFragmentAdapter(
    private var onItemViewClickListener: PeoplesFragment.OnItemViewClickListener,
    private var onNewPage: PeoplesFragment.OnNewPage
) : RecyclerView.Adapter<PeoplesFragmentAdapter.MainViewHolder>() {

    private var persons: Persons = Persons()
    private var personList = mutableListOf<Person>()

    fun setPeople(data: Persons) {
        val firstItemPosition = personList.size
        personList.addAll(data.persons)
        persons = data
        notifyItemRangeInserted(firstItemPosition, persons.persons.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_peoples_recycler_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        if (position == personList.size - 1 && persons.page < persons.total_pages) {
            onNewPage.getPage(persons.isRus, persons.adult, persons.page + 1)
        }
        holder.bind(personList[position])
    }

    override fun getItemCount(): Int {
        return personList.size
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(person: Person) {
            itemView.apply {
                with(person) {
                    findViewById<TextView>(R.id.item_title_peoples).text = name
                    findViewById<TextView>(R.id.item_vote_average_peoples).text =
                        popularity.toString()
                    setOnClickListener {
                        onItemViewClickListener?.onItemViewClick(person)
                    }
                    Picasso
                        .get()
                        .load(profile_path)
                        .into(findViewById<ImageView>(R.id.poster_peoples))
                }
            }
        }
    }
}