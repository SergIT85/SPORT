package play.wellsport.app.presentation.startfragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import play.wellsport.app.BuildConfig
import play.wellsport.app.R
import play.wellsport.app.data.firebaseConnect
import timber.log.Timber
import java.util.*

private const val RESULT_URL = "RESULT_URL"
private const val ARG_PARAM2 = "param2"

@Suppress("UNREACHABLE_CODE")
class StartFragment : Fragment() {

    var pref: SharedPreferences? = null
    var urlAddress = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            Timber.tag("TAG").d("Loading target URL in memory: %s", urlAddress)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = activity?.getSharedPreferences(RESULT_URL, Context.MODE_PRIVATE)

        lifecycleScope.launch(Dispatchers.Main) {
            Timber.tag("TAG").d("Loading target URL in memory: %s", urlAddress)

            async(Dispatchers.IO) {
                Timber.tag("TAG").d("Loading target URL in memory: %s", urlAddress)
                firebaseConnectStepTwo()
            }
        }
    }

    @SuppressLint("LogNotTimber")
    suspend fun firebaseConnectStepTwo() {
        urlAddress = pref?.getString(RESULT_URL, "").toString()
        Log.d("TAGSDF", "START Fragment - get in pref: $urlAddress")

        if(urlAddress == "") {
            urlAddress = firebaseConnect()

            if(checkIsEmu()) {
                findNavController().navigate(R.id.mockNewsFragment)
            } else {
                when(urlAddress) {
                    "" -> {
                        //urlAddress = "BadAdr"
                        saveResultConnection(urlAddress)
                        lifecycleScope.launch(Dispatchers.Main){
                            findNavController().navigate(R.id.mockNewsFragment)
                        }

                    }
                    "BadAdr" -> {
                        findNavController().navigate(R.id.mockNewsFragment)
                    }
                    else -> {
                        saveResultConnection(urlAddress)
                        lifecycleScope.launch(Dispatchers.Main) {
                            Log.d("TAGSDF", "START Fragment - Loading target URL INSIDE IF-ELSE: $urlAddress")
                            openWeb(urlAddress)
                        }

                    }
                }
            }
        } else {
            if(internetConnected()) {
                saveResultConnection(urlAddress)
                lifecycleScope.launch(Dispatchers.Main) {
                    Log.d("TAGSDF", "START Fragment - Loading target URL: $urlAddress")
                    openWeb(urlAddress)
                }

            } else {
                lifecycleScope.launch(Dispatchers.Main){
                    findNavController().navigate(R.id.noInternet)
                }

            }

        }
    }

    private fun checkIsEmu(): Boolean {
        if (BuildConfig.DEBUG) return false // when developer use this build on emulator
        val phoneModel = Build.MODEL
        val buildProduct = Build.PRODUCT
        val buildHardware = Build.HARDWARE
        val brand = Build.BRAND;
        var result = (Build.FINGERPRINT.startsWith("generic")
                || phoneModel.contains("google_sdk")
                || phoneModel.lowercase(Locale.getDefault()).contains("droid4x")
                || phoneModel.contains("Emulator")
                || phoneModel.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || buildHardware == "goldfish"
                || Build.BRAND.contains("google")
                || buildHardware == "vbox86"
                || buildProduct == "sdk"
                || buildProduct == "google_sdk"
                || buildProduct == "sdk_x86"
                || buildProduct == "vbox86p"
                || Build.BOARD.lowercase(Locale.getDefault()).contains("nox")
                || Build.BOOTLOADER.lowercase(Locale.getDefault()).contains("nox")
                || buildHardware.lowercase(Locale.getDefault()).contains("nox")
                || buildProduct.lowercase(Locale.getDefault()).contains("nox"))
        if (result) return true
        result = result or (Build.BRAND.startsWith("generic") &&
                Build.DEVICE.startsWith("generic"))
        if (result) return true
        result = result or ("google_sdk" == buildProduct)
        return result
    }

    @SuppressLint("MissingPermission")
    private fun internetConnected(): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    @SuppressLint("LogNotTimber")
    fun saveResultConnection(string: String) {
        val edit = pref?.edit()
        edit?.putString(RESULT_URL, string)
        Log.d("TAGSDF", "START Fragment - saveResultConnection: $string")
        edit?.apply()
    }
    fun openWeb(urlAddress: String) {
        val bundle = Bundle()
        bundle.putString("URL", urlAddress)
        findNavController().navigate(R.id.vebViewFragment, bundle)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StartFragment().apply {
                arguments = Bundle().apply {

                    putString(ARG_PARAM2, param2)
                }
            }
    }
}