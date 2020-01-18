package com.panat.mvvm.retrofit.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.panat.mvvm.retrofit.di.provideRetrofit
import com.panat.mvvm.retrofit.model.GithubEvents
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivityViewModel : ViewModel() {

    var events = MutableLiveData<List<GithubEvents>>()

    fun loadEvents() {
        val call = provideRetrofit()
        call.getEvents().enqueue(object : Callback<List<GithubEvents>> {
            override fun onResponse(
                call: Call<List<GithubEvents>>,
                response: Response<List<GithubEvents>>
            ) {
                events.postValue(response.body())
            }

            override fun onFailure(call: Call<List<GithubEvents>>, t: Throwable) {
                println("GithubEvents onFailure")
            }
        })
        call.getEvents().cancel()
    }
}