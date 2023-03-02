package com.teamzzong.hacker.shared

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.teamzzong.hacker.shared.databinding.ActivityWebViewBinding
import timber.log.Timber

class WebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val loadUrl =
            intent.getStringExtra("loadUrl") ?: throw IllegalArgumentException("Not a valid url")

        binding.webview.settings.apply {
            javaScriptCanOpenWindowsAutomatically = true
            javaScriptEnabled = true
            cacheMode = android.webkit.WebSettings.LOAD_DEFAULT
            domStorageEnabled = true
            mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            loadWithOverviewMode = true
            useWideViewPort = true
            builtInZoomControls = false
            displayZoomControls = false
        }

        binding.webview.apply {
            webViewClient = HackerWebViewClient(::logEvent)
            loadUrl(loadUrl)
        }
    }

    private fun logEvent(log: String) {
        Timber.d("HackerWebView Event : $log")
    }

    companion object {
        fun getIntent(context: Context, url: String) =
            Intent(context, WebViewActivity::class.java)
                .putExtra("loadUrl", url)
    }
}
