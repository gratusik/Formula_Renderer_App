package com.gratus.formularendererapp.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gratus.formularendererapp.di.factory.ViewModelFactory
import com.gratus.formularendererapp.di.key.ViewModelKey
import com.gratus.formularendererapp.viewModel.activity.FormulaViewModel
import com.gratus.formularendererapp.viewModel.activity.MoreViewModel
import com.gratus.formularendererapp.viewModel.activity.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(splashViewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FormulaViewModel::class)
    abstract fun bindFormulaViewModel(formulaViewModel: FormulaViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MoreViewModel::class)
    abstract fun bindMoreViewModel(moreViewModel: MoreViewModel): ViewModel

}