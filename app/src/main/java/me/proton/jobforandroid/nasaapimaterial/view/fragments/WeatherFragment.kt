package me.proton.jobforandroid.nasaapimaterial.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.google.android.material.snackbar.Snackbar
import me.proton.jobforandroid.nasaapimaterial.R
import me.proton.jobforandroid.nasaapimaterial.databinding.FragmentWeatherBinding
import me.proton.jobforandroid.nasaapimaterial.view.MainActivity
import me.proton.jobforandroid.nasaapimaterial.viewmodel.AppState
import me.proton.jobforandroid.nasaapimaterial.viewmodel.OneBigFatViewModel

class WeatherFragment : Fragment() {

    private var _binding: FragmentWeatherBinding? = null
    private val binding: FragmentWeatherBinding
        get() {
            return _binding!!
        }

    lateinit var oneBigFatViewModel: OneBigFatViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        oneBigFatViewModel = (context as MainActivity).oneBigFatViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        oneBigFatViewModel.getLiveData().observe(viewLifecycleOwner, { render(it) })
        binding.chipsGroup.setOnCheckedChangeListener { _, checkedId ->
            binding.contentText.text = ""
            when (checkedId) {
                R.id.chipWeatherToday -> {
                    binding.chipsGroup.check(R.id.chipWeatherToday)
                    oneBigFatViewModel.getSolarFlare(TODAY)

                }
                R.id.chipWeatherLastMonth -> {
                    binding.chipsGroup.check(R.id.chipWeatherLastMonth)
                    oneBigFatViewModel.getSolarFlare(MONTH)

                }
                else -> oneBigFatViewModel.getSolarFlare(MONTH)
            }
        }
    }

    private fun render(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                binding.loadingImageView.visibility = View.GONE
                Snackbar.make(binding.root, appState.error.toString(), Snackbar.LENGTH_SHORT).show()
            }
            is AppState.Loading -> {
                binding.loadingImageView.visibility = View.VISIBLE
                binding.loadingImageView.load(R.drawable.progress_animation)
            }
            is AppState.SuccessWeather -> {
                binding.loadingImageView.visibility = View.GONE
                binding.contentText.visibility = View.VISIBLE
                setData(appState)
            }
            else -> {}
        }
    }

    private fun setData(appState: AppState.SuccessWeather) {
        binding.contentText.text = appState.solarFlareResponseData.toString()
    }

    companion object {
        fun newInstance(): WeatherFragment {
            return WeatherFragment()
        }

        private const val TODAY = 0
        private const val MONTH = 30
    }
}