package com.gratus.formularendererapp

import android.content.Context
import android.net.ConnectivityManager
import com.gratus.formularendererapp.di.component.AppComponent
import com.gratus.formularendererapp.di.component.DaggerAppComponent
import com.gratus.formularendererapp.di.modules.InternetModule
import com.gratus.formularendererapp.di.modules.RoomModule
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class BaseApplication : DaggerApplication() {
    private lateinit var appComponent: AppComponent
    private lateinit var connectivityManager: ConnectivityManager

    override fun applicationInjector(): AndroidInjector<DaggerApplication> {
        connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        appComponent = DaggerAppComponent.builder().application(this)
            .roomModule(RoomModule(this))
            .internetModule(InternetModule(connectivityManager))
            .build()
        appComponent.inject(this)
        return appComponent
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }
}