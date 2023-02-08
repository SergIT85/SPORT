package play.wellsport.app.presentation.webview

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import play.wellsport.app.R
import play.wellsport.app.databinding.FragmentWebViewBinding

class WebViewFragment : Fragment(R.layout.fragment_web_view)  {

    private var _binding: FragmentWebViewBinding? = null
    private val binding get() = _binding!!
    private var urlAddress: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWebViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled", "LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        urlAddress = requireArguments().getString("URL").toString()

        Log.d("TAGSDF", "WEBView - Loading target URL: $urlAddress")
        val webActivity = binding.webView

        webActivity.webViewClient = WebViewClient()

        with(webActivity.settings) {
            javaScriptEnabled = true
            domStorageEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            loadWithOverviewMode = true
            useWideViewPort = true
            databaseEnabled = true
            setSupportZoom(false)
            allowFileAccess = true
            allowContentAccess = true
            loadWithOverviewMode = true
            useWideViewPort = true
        }

        CookieManager.getInstance().setAcceptThirdPartyCookies(webActivity, true)
        CookieManager.getInstance().flush()

        val regPattern = Regex("""(?:https?|ftp)://\S+""")
        val containUrl = regPattern.containsMatchIn(urlAddress)

        if (!urlAddress.isEmpty() && containUrl) {
            webActivity.loadUrl(urlAddress)
        } else {
            findNavController().navigate(R.id.mockNewsFragment)
        }


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val webActivity = binding.webView
                if (webActivity.canGoBack()){
                    webActivity.goBack()
                } else {

                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }



    companion object {

    }
}