package com.teamzzong.hacker.shared

import android.annotation.TargetApi
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.webkit.*

class HackerWebViewClient(val writeLog: (String) -> Unit) : WebViewClient() {
    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        writeLog("onPageStarted : ${url}\n")
        super.onPageStarted(view, url, favicon)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        writeLog("onPageFinished : ${url}\n")
        super.onPageFinished(view, url)
    }

    override fun onLoadResource(view: WebView?, url: String?) {
        writeLog("onLoadResource : ${url}\n")
        super.onLoadResource(view, url)
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        writeLog("onReceivedError : ${error?.description.toString()}\n")
        super.onReceivedError(view, request, error)
    }

    override fun shouldInterceptRequest(
        view: WebView?,
        request: WebResourceRequest?
    ): WebResourceResponse? {
        writeLog("shouldInterceptRequest : ${request?.url.toString()}\n")
        return super.shouldInterceptRequest(view, request)
    }

    override fun shouldOverrideUrlLoading(
        view: WebView?,
        request: WebResourceRequest?
    ): Boolean {
        writeLog("shouldOverrideUrlLoading : ${request?.url.toString()}\n")
        return super.shouldOverrideUrlLoading(view, request)
    }

    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
        super.onReceivedSslError(view, handler, error)
        writeLog("onReceivedSslError : ${error?.toString()}\n")
    }
}
