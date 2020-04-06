package com.diegoprado.desafio_android_diego_prado.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diegoprado.desafio_android_diego_prado.BuildConfig
import com.diegoprado.desafio_android_diego_prado.data.model.HeroEntity
import com.diegoprado.desafio_android_diego_prado.data.model.Results
import com.diegoprado.desafio_android_diego_prado.domain.CreateRequest
import com.diegoprado.desafio_android_diego_prado.data.util.ConvertMD5
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class PersonViewModel(): ViewModel() {

    var list: ArrayList<Results> = ArrayList()
    val heroRequest = CreateRequest().myRequest
    var personData: MutableLiveData<ArrayList<Results>> = MutableLiveData()
    var timeStamp: Long = 0
    lateinit var stringToHash: String
    lateinit var hash: String

    fun requestProject(): Call<HeroEntity> {

        timeStamp = Date().time

        stringToHash = timeStamp.toString() + BuildConfig.MARVEL_PRIVATE_KEY + BuildConfig.MARVEL_PUBLIC_KEY
        hash = ConvertMD5().md5(stringToHash)

        return heroRequest.getResult(timeStamp.toString(),
            BuildConfig.MARVEL_PUBLIC_KEY, hash)
    }

    fun createAdapter() {
        val personObject = requestProject()

        personObject.enqueue(object : Callback<HeroEntity> {
            override fun onFailure(call: Call<HeroEntity>, t: Throwable) {
                Log.e("ERROR-REPONSE", "Erro ao baixar dados. Mensagem: " + t.message)
            }

            override fun onResponse(call: Call<HeroEntity>, response: Response<HeroEntity>) {

                response.body().let {

                    it?.data?.let {data ->

                        data.results?.forEach { res ->
                            list.add(res)
                        }
                    }
                }
                    personData.value = list
                }

        })
    }
}