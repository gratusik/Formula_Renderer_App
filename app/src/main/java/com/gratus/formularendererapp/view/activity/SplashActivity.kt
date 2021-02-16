package com.gratus.formularendererapp.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.gratus.formularendererapp.R
import com.gratus.formularendererapp.databinding.ActivitySplashBinding
import com.gratus.formularendererapp.view.base.BaseActivity
import com.gratus.formularendererapp.viewModel.activity.SplashViewModel
import javax.inject.Inject

class SplashActivity : BaseActivity() {
    lateinit var activitySplashBinding: ActivitySplashBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySplashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        splashViewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
        activitySplashBinding.splashViewModel = splashViewModel
        activitySplashBinding.setLifecycleOwner(this)
        setupPermissions()
        networkCheck(activitySplashBinding.parentRL)
        intentFormulaActivity()
    }

    private fun intentFormulaActivity() {
        Thread.sleep(1000)
        val intent = Intent(this, FormulaActivity::class.java)
        startActivity(intent)
        finish()
    }
}