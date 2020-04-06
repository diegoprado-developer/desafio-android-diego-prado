package com.diegoprado.desafio_android_diego_prado.data.model

import com.google.gson.annotations.SerializedName


class HeroEntity(){

    @SerializedName("code")
    var code: Int? = null

    @SerializedName("data")
    var data: Data? = null
}

class Data(){

    @SerializedName("count")
    var count: Int? = null

    @SerializedName("results")
    var results: List<Results>? = null
}

class Results(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("thumbnail")
    var thumbnail: Thumbnail
){
}

class Thumbnail(var path:String,var extension:String)