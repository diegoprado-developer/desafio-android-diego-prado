package com.diegoprado.desafio_android_diego_prado.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diegoprado.desafio_android_diego_prado.BuildConfig
import com.diegoprado.desafio_android_diego_prado.data.model.ComicsEntity
import com.diegoprado.desafio_android_diego_prado.data.model.HeroEntity
import com.diegoprado.desafio_android_diego_prado.data.model.ResultComics
import com.diegoprado.desafio_android_diego_prado.data.model.Results
import com.diegoprado.desafio_android_diego_prado.data.util.ConvertMD5
import com.diegoprado.desafio_android_diego_prado.domain.CreateRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.FieldPosition
import java.util.*
import kotlin.collections.ArrayList

class ComisViewModel(): ViewModel() {

    var list: ArrayList<ResultComics> = ArrayList()
    val comicRequest = CreateRequest().myRequest
    var comicData: MutableLiveData<ArrayList<ResultComics>> = MutableLiveData()
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

    fun getComisData(heroId: Int) {
        val comicObject = requestProject(heroId)

        comicObject.enqueue(object : Callback<ComicsEntity> {
            override fun onFailure(call: Call<ComicsEntity>, t: Throwable) {
                Log.e("ERROR-REPONSE", "Erro ao baixar dados. Mensagem: " + t.message)
            }

            override fun onResponse(call: Call<ComicsEntity>, response: Response<ComicsEntity>) {

                response.body().let {

                    it?.dataComics?.let {data ->

                        data.resultsComics?.forEach { comics ->

                            if( comics.heroId == heroId){
                                list.add(comics)
                            }
                        }
                        comicData.value = list
                    }
                }
            }

        })
    }

}
