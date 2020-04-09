package com.diegoprado.desafio_android_diego_prado.ui.viewmodel

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diegoprado.desafio_android_diego_prado.BuildConfig
import com.diegoprado.desafio_android_diego_prado.data.model.*
import com.diegoprado.desafio_android_diego_prado.data.util.ConvertMD5
import com.diegoprado.desafio_android_diego_prado.domain.CreateRequest
import com.diegoprado.desafio_android_diego_prado.ui.IResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.FieldPosition
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log

class ComicsViewModel(): ViewModel() {

    lateinit var heroComics: ResultComics
    val comicRequest = CreateRequest().myRequest
    var comicData: MutableLiveData<ResultComics> = MutableLiveData()
    lateinit var price: Prices
    var timeStamp: Long = 0
    lateinit var stringToHash: String
    lateinit var hash: String

    fun requestProject(heroId: Int): Call<ComicsEntity> {

        timeStamp = Date().time

        stringToHash = timeStamp.toString() + BuildConfig.MARVEL_PRIVATE_KEY + BuildConfig.MARVEL_PUBLIC_KEY
        hash = ConvertMD5().md5(stringToHash)

        return comicRequest.getResultComic(heroId.toString(), timeStamp.toString(),
            BuildConfig.MARVEL_PUBLIC_KEY, hash)
    }

    fun getComicsData(heroId: Int, context: IResponse) {
        val comicObject = requestProject(heroId)


        comicObject.enqueue(object : Callback<ComicsEntity> {
            override fun onFailure(call: Call<ComicsEntity>, t: Throwable) {
                context.OnError("Falha ao Acessar API")

            }

            override fun onResponse(call: Call<ComicsEntity>, response: Response<ComicsEntity>) {

                response.body().let {

                    it?.dataComics?.let {data ->

                        data.resultsComics.let { resultComic ->

                            if (!resultComic.isNullOrEmpty()){
                                heroComics = resultComic.get(0)
                                comicData.value = heroComics
                            }else{
                                context.OnError("Não possui informações")

                            }
                        }

                    }
                }
            }

        })
    }

}
