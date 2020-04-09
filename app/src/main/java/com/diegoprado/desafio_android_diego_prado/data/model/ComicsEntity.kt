package com.diegoprado.desafio_android_diego_prado.data.model

import com.google.gson.annotations.SerializedName

class ComicsEntity {

    @SerializedName("code")
    var code: Int? = null

    @SerializedName("data")
    var dataComics: DataComics? = null
}

class DataComics(){

    @SerializedName("count")
    var count: Int? = null

    @SerializedName("results")
    var resultsComics: List<ResultComics>? = null
}

class ResultComics(
    @SerializedName("id")
    var id: Int,
    @SerializedName("digitalId")
    var heroId: Int,
    @SerializedName("title")
    var title: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("thumbnail")
    var thumbnail: ThumbnailComics,
    @SerializedName("prices")
    var prices: List<Prices>? = null
){
}

class ThumbnailComics(var path:String,var extension:String)

class Prices(
    @SerializedName("type")
    var type: String,
    @SerializedName("price")
    var price: Double)