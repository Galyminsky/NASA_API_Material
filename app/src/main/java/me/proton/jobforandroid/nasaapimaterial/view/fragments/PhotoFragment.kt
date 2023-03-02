package me.proton.jobforandroid.nasaapimaterial.view.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.google.android.material.snackbar.Snackbar
import me.proton.jobforandroid.nasaapimaterial.BuildConfig
import me.proton.jobforandroid.nasaapimaterial.BuildConfig.NASA_API_KEY
import me.proton.jobforandroid.nasaapimaterial.R
import me.proton.jobforandroid.nasaapimaterial.databinding.FragmentFotoBinding
import me.proton.jobforandroid.nasaapimaterial.view.MainActivity
import me.proton.jobforandroid.nasaapimaterial.viewmodel.AppState
import me.proton.jobforandroid.nasaapimaterial.viewmodel.OneBigFatViewModel

class PhotoFragment: Fragment() {

    private var _binding: FragmentFotoBinding? = null
    private val binding: FragmentFotoBinding
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
        _binding = FragmentFotoBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        oneBigFatViewModel.getLiveData().observe(viewLifecycleOwner,{ render(it) })
        binding.chipsGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.chipToday -> {
                    binding.chipsGroup.check(R.id.chipToday)
                    oneBigFatViewModel.getPODFromServer((TODAY))

                }
                R.id.chipYesterday -> {
                    binding.chipsGroup.check(R.id.chipYesterday)
                    oneBigFatViewModel.getPODFromServer((YESTERDAY))

                }
                R.id.chipDayBeforeYesterday -> {
                    binding.chipsGroup.check(R.id.chipDayBeforeYesterday)
                    oneBigFatViewModel.getPODFromServer((BEFORE_YESTERDAY))
                }
                R.id.chipEarthToday -> {
                    binding.chipsGroup.check(R.id.chipEarthToday)
                    oneBigFatViewModel.getEpic()
                }
                R.id.chipMarsToday -> {
                    binding.chipsGroup.check(R.id.chipMarsToday)
                    oneBigFatViewModel.getMarsPicture()
                }
                else ->  oneBigFatViewModel.getPODFromServer((TODAY))
            }
        }
    }

    private fun render(appState: AppState) {
        when(appState){
            is AppState.Error ->
                Snackbar.make(binding.root, appState.error.toString(), Snackbar.LENGTH_SHORT).show()
            is AppState.Loading -> {
                binding.customImageView.load(R.drawable.progress_animation)
            }
            is AppState.SuccessPOD -> {
                setData(appState)
            }
            is AppState.SuccessEarthEpic -> {
                // немного магии датамайнинга
                val strDate = appState.serverResponseData.last().date.split(" ").first()
                val image =appState.serverResponseData.last().image
                val url = "https://api.nasa.gov/EPIC/archive/natural/" +
                        strDate.replace("-","/",true) +
                        "/png/" +
                        "$image" +
                        ".png?api_key=${BuildConfig.NASA_API_KEY}"
                binding.customImageView.load(url)
            }
            is AppState.SuccessMars -> {
                if(appState.serverResponseData.photos.isEmpty()){
                    Snackbar.make(binding.root, "В этот день curiosity не сделал ни одного снимка", Snackbar.LENGTH_SHORT).show()
                }else{
                    val url = appState.serverResponseData.photos.first().imgSrc
                    binding.customImageView.load(url)
                }

            }
            else -> {}
        }
    }


    private fun setData(data: AppState.SuccessPOD)  {
        val url = data.serverResponseData.hdurl
        if (url.isNullOrEmpty()) {
            val videoUrl = data.serverResponseData.url
            videoUrl?.let { showAVideoUrl(it) }
        } else {
            binding.customImageView.load(url)
        }
    }

    private fun showAVideoUrl(videoUrl: String) = with(binding) {
        customImageView.visibility = View.GONE
        videoOfTheDay.visibility = View.VISIBLE
        videoOfTheDay.text = "Сегодня у нас без картинки дня, но есть  видео дня! " +
                "${videoUrl.toString()} \n кликни >ЗДЕСЬ< чтобы открыть в новом окне"
        videoOfTheDay.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(videoUrl)
            }
            startActivity(i)
        }
    }


    companion object{
        fun newInstance(): PhotoFragment {
            return PhotoFragment()
        }
        private const val TODAY = 0
        private const val YESTERDAY = 1
        private const val BEFORE_YESTERDAY = 2
    }
}