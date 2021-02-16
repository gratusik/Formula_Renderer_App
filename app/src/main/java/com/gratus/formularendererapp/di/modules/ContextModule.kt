package com.gratus.formularendererapp.di.modules

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

@Module
abstract class ContextModule(private val context: Context) {
    @Binds
    abstract fun bindContext(application: Application): Context
}