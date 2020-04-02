package com.diegoprado.desafio_android_diego_prado

import android.net.Uri
import android.widget.ImageView

data class Person(var id: Int,
                    var name: String,
                    var description: String,
                    var thumbnail: Uri){

}