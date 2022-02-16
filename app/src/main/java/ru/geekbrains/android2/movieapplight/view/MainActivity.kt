package ru.geekbrains.android2.movieapplight.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ru.geekbrains.android2.movieapplight.R
import ru.geekbrains.android2.movieapplight.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(getLayoutInflater())
        val view = binding.getRoot()
        setContentView(view)
        initToolBar()
        savedInstanceState ?: run {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    private fun initToolBar() {
        val toolbar = binding.activityToolbar
        setSupportActionBar(toolbar)
        val fragmentManager = supportFragmentManager
        fragmentManager.addOnBackStackChangedListener {
            if (fragmentManager.backStackEntryCount > 0) {
                // show back button
                supportActionBar?.let {
                    it.setDisplayHomeAsUpEnabled(true)
                    it.setHomeButtonEnabled(true)
                    toolbar.setNavigationOnClickListener { v: View? -> onBackPressed() }
                }
            } else {
                supportActionBar?.let {
                    it.setDisplayHomeAsUpEnabled(false)
                    it.setHomeButtonEnabled(false)
                }
            }
        }
    }
}