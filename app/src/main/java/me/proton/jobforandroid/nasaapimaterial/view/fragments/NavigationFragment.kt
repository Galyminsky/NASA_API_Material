package me.proton.jobforandroid.nasaapimaterial.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import me.proton.jobforandroid.nasaapimaterial.R
import me.proton.jobforandroid.nasaapimaterial.databinding.FragmentNavigationBinding
import me.proton.jobforandroid.nasaapimaterial.view.MainActivity
import me.proton.jobforandroid.nasaapimaterial.view.Navigation

class NavigationFragment : Fragment() {

    lateinit var navigation: Navigation

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigation = (context as MainActivity).navigation
    }

    private var _binding: FragmentNavigationBinding? = null
    private val binding: FragmentNavigationBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNavigationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_photo -> navigation.showFragment(PhotoFragment.newInstance(), false)
                R.id.action_weather -> navigation.showFragment(WeatherFragment.newInstance(), false)
            }
            true
        }
        binding.navigationView.selectedItemId = R.id.action_photo;
    }

    companion object {
        fun newInstance(): NavigationFragment {
            return NavigationFragment()
        }
    }
}