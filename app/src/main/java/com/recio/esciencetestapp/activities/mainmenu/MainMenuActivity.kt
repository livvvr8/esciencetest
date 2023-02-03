package com.recio.esciencetestapp.activities.mainmenu

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.recio.esciencetestapp.R
import com.recio.esciencetestapp.activities.baseactivity.BaseActivity
import com.recio.esciencetestapp.databinding.ActivityMainMenuBinding
import com.recio.esciencetestapp.fragments.mainmenu.NewsFragment
import com.recio.esciencetestapp.fragments.mainmenu.WeatherFragment

class MainMenuActivity : BaseActivity<ActivityMainMenuBinding>() {

    private lateinit var currentFragment: Fragment
    private var newsFragment: NewsFragment = NewsFragment()
    private var weatherFragment: WeatherFragment = WeatherFragment()

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun getActivityBinding(): ActivityMainMenuBinding {
        return ActivityMainMenuBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showDefaultFragment()
        instantiateBottomNavigationView()
    }

    private fun showDefaultFragment() {
        currentFragment = newsFragment
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, currentFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

        binding.textToolBarHeader.text = getString(R.string.menu_news)
    }

    private fun replaceCurrentFragment(currentFragment: Fragment, newFragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, newFragment)
            .remove(currentFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .disallowAddToBackStack()
            .commit()
    }

    private fun instantiateBottomNavigationView() {
        bottomNavigationView = binding.bottomNavigationView
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_news -> {
                    if (currentFragment!=newsFragment) {
                        replaceCurrentFragment(currentFragment, newsFragment)
                        currentFragment = newsFragment
                        binding.textToolBarHeader.text = getString(R.string.menu_news)
                    }
                }
                R.id.menu_weather -> {
                    if (currentFragment!=weatherFragment) {
                        replaceCurrentFragment(currentFragment, weatherFragment)
                        currentFragment = weatherFragment

                        binding.textToolBarHeader.text = getString(R.string.menu_weather)
                    }
                }
                else -> {
                    showDefaultFragment()
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (currentFragment==newsFragment){
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.confirm_exit_dialog_title))
            builder.setMessage(getString(R.string.confirm_exit_dialog_message))
            builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
                super.onBackPressed()
            }
            builder.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            val alert: AlertDialog = builder.create()
            alert.show()
        } else {
            bottomNavigationView.selectedItemId = R.id.menu_news
        }
    }
}