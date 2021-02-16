package com.gratus.formularendererapp.viewModel.base

import androidx.lifecycle.ViewModel
import com.gratus.formularendererapp.util.networkManager.NetworkOnlineCheck
import com.gratus.formularendererapp.util.pref.AppPreferencesHelper
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {
    @Inject
    lateinit var prefs: AppPreferencesHelper

    @Inject
    lateinit var networkOnlineCheck: NetworkOnlineCheck
    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose()
        }
        super.onCleared()
    }

    fun isNetworkConnected(): Boolean {
        return networkOnlineCheck.isNetworkOnline
    }
}
