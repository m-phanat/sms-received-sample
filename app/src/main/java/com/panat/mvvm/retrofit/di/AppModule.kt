package com.panat.mvvm.retrofit.di

import com.panat.mvvm.retrofit.service.ApiService
import com.panat.mvvm.retrofit.viewModel.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    viewModel { MainActivityViewModel( get() ) }
    factory { provideRetrofit() }
}

fun provideRetrofit(): ApiService {
    return Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(ApiService::class.java)
}
