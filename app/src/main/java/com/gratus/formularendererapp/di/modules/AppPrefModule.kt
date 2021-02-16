package com.gratus.formularendererapp.di.modules

import com.gratus.formularendererapp.util.pref.AppPrefHelper
import com.gratus.formularendererapp.util.pref.AppPreferencesHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppPrefModule {
    @Provides
    @Singleton
    fun providePreferencesHelper(appPreferencesHelper: AppPreferencesHelper): AppPrefHelper {
        return appPreferencesHelper
    }
}