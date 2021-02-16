package com.gratus.formularendererapp.di.modules

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.gratus.formularendererapp.service.CheckService
import com.gratus.formularendererapp.service.ImageService
import com.gratus.formularendererapp.util.constants.ServiceConstants
import com.gratus.formularendererapp.util.interceptor.AppInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class])
object NetworkModule {
    @Provides
    @Singleton
    @JvmStatic
    fun provideGson(): Gson {
        val builder =
            GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return builder.setLenient().create()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ServiceConstants.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun getRequestHeader(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(AppInterceptor())
            .connectTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .readTimeout(300, TimeUnit.SECONDS)
        return httpClient.build()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideCheckService(retrofit: Retrofit): CheckService {
        return retrofit.create(CheckService::class.java)
    }


    @Provides
    @Singleton
    @JvmStatic
    fun provideImageService(retrofit: Retrofit): ImageService {
        return retrofit.create(ImageService::class.java)
    }
}