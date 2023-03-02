package com.teamzzong.hacker.presentation.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.teamzzong.hacker.databinding.ActivityMakersWebViewBinding
import com.teamzzong.hacker.shared.HackerWebViewClient
import com.teamzzong.hacker.ui.intent.stringExtra
import timber.log.Timber

class MakersWebViewActivity : AppCompatActivity() {
    private val loadUrl by stringExtra()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMakersWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.webView.settings.apply {
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
        binding.webView.apply {
            webViewClient = HackerWebViewClient(::logEvent)
            loadUrl(loadUrl ?: "")
        }
        binding.btnBack.setOnClickListener { finish() }
    }

    private fun logEvent(log: String) {
        Timber.d("MakersWebView Event : $log")
    }

    companion object {
        fun getIntent(context: Context, url: String) =
            Intent(context, MakersWebViewActivity::class.java)
                .putExtra("loadUrl", url)
    }
}
