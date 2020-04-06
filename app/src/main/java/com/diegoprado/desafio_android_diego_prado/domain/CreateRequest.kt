package com.diegoprado.desafio_android_diego_prado.domain

import com.diegoprado.desafio_android_diego_prado.data.RequestApi
import com.diegoprado.desafio_android_diego_prado.data.request.RequestProvider

class CreateRequest {

    var myRequest: RequestProvider

    init {
        myRequest = RequestApi().request()
    }
}