package com.diegoprado.desafio_android_diego_prado.data

import com.diegoprado.desafio_android_diego_prado.data.request.RequestProvider
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RequestApi {

    private val BASIC_URL = "https://gateway.marvel.com/"

    private var accessAPI: Retrofit = Retrofit.Builder()
        .baseUrl(BASIC_URL)
        .addConverterFactory(GsonConverterFactory.create())
//        .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
        .build()

    fun request(): RequestProvider =
        accessAPI.create(RequestProvider::class.java)
}