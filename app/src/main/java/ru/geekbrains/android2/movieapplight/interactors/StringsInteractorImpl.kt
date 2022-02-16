package ru.geekbrains.android2.movieapplight.interactors

import android.content.Context
import ru.geekbrains.android2.movieapplight.R

class StringsInteractorImpl(context: Context) : StringsInteractor {
    override val strNowPlaying = context.getString(R.string.now_playing)
    override val strPopular = context.getString(R.string.popular)
    override val strTopRated = context.getString(R.string.top_rated)
    override val strUpcoming = context.getString(R.string.upcoming)
}