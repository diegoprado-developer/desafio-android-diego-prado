package com.diegoprado.desafio_android_diego_prado

import android.net.Uri
import android.widget.ImageView
import org.json.JSONObject

data class Person(var id: Int,
                    var name: String,
                    var description: String,
                    var thumbnail: Thumbnail){

}

data class Thumbnail(var path:String,var extension:String)