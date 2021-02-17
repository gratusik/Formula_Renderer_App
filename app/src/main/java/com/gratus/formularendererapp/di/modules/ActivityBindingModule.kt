package com.gratus.formularendererapp.di.modules

import com.gratus.formularendererapp.view.activity.FormulaActivity
import com.gratus.formularendererapp.view.activity.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector
    abstract fun bindSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [FormulaAdapterModule::class])
    abstract fun bindFormulaActivity(): FormulaActivity
}
