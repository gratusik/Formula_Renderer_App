package com.gratus.formularendererapp.view.activity

import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.gratus.formularendererapp.R
import com.gratus.formularendererapp.databinding.ActivityMoreBinding
import com.gratus.formularendererapp.util.constants.ServiceConstants
import com.gratus.formularendererapp.view.base.BaseActivity
import com.gratus.formularendererapp.viewModel.activity.MoreViewModel
import javax.inject.Inject


class MoreActivity : BaseActivity() {

    lateinit var activityMoreBinding: ActivityMoreBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var moreViewModel: MoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMoreBinding = DataBindingUtil.setContentView(this, R.layout.activity_more)
        moreViewModel = ViewModelProvider(this).get(MoreViewModel::class.java)
        activityMoreBinding.moreViewModel = moreViewModel
        activityMoreBinding.setLifecycleOwner(this)
        networkCheck(activityMoreBinding.parentRL)
        var searchUrl: String  = intent.getStringExtra("formulaText").toString()
        searchUrl =
            ServiceConstants.BASE_URL + ServiceConstants.SEARCH_URL + "?action=query&list=search&srsearch=" + searchUrl + "&format=json"
        activityMoreBinding.backArrowImg.setOnClickListener(View.OnClickListener { view -> onBackPressed() })
        activityMoreBinding.webview.setWebViewClient(MyBrowser())
        val webSettings: WebSettings = activityMoreBinding.webview.settings
        webSettings.javaScriptEnabled = true
        activityMoreBinding.webview.loadUrl("https://en.wikipedia.org/w/api.php?action=parse&page=Pet_door&prop=wikitext&formatversion=2")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private class MyBrowser : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }
}