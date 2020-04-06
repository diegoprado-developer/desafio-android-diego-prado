package com.diegoprado.desafio_android_diego_prado.data.request

import com.diegoprado.desafio_android_diego_prado.data.model.HeroEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RequestProvider {

    @GET("/v1/public/characters")
    fun getResult(@Query("ts")ts: String,
            @Query("apikey")apikey:String,
            @Query("hash")hash: String): Call<HeroEntity>
}