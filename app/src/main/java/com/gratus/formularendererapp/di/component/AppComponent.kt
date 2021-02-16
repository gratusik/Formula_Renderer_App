package com.gratus.formularendererapp.di.component

import android.app.Application
import com.gratus.formularendererapp.BaseApplication
import com.gratus.formularendererapp.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class, NetworkModule::class,
        InternetModule::class, AppPrefModule::class, ContextModule::class,
        ActivityBindingModule::class, RoomModule::class]
)
interface AppComponent : AndroidInjector<DaggerApplication> {
    fun inject(application: BaseApplication)
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent

        fun internetModule(internetModule: InternetModule): Builder

        fun roomModule(roomModule: RoomModule): Builder

    }
}