package me.proton.jobforandroid.nasaapimaterial.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import me.proton.jobforandroid.nasaapimaterial.R
import me.proton.jobforandroid.nasaapimaterial.view.fragments.NavigationFragment
import me.proton.jobforandroid.nasaapimaterial.viewmodel.OneBigFatViewModel

class MainActivity : AppCompatActivity() {
    val oneBigFatViewModel by lazy {
        ViewModelProvider(this).get(OneBigFatViewModel::class.java)
    }

    lateinit var navigation: Navigation
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation = Navigation(supportFragmentManager)
        navigation.showNavigationFragment(NavigationFragment.newInstance())
    }
}