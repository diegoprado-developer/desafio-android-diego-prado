package com.diegoprado.desafio_android_diego_prado

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.diegoprado.desafio_android_diego_prado.util.ConvertMD5
import org.json.JSONObject
import java.util.*

class MainActivity : AppCompatActivity() {

    val BASIC_URL = "https://gateway.marvel.com:443/v1/public/characters?"
    var list: MutableList<Person> = emptyList<Person>().toMutableList()
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: PersonAdapter
    lateinit var requestQueue: RequestQueue
    var timeStamp: Long = 0
    lateinit var stringToHash: String
    lateinit var hash: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestQueue = Volley.newRequestQueue(this@MainActivity)

        recyclerView = findViewById(R.id.rvPersonsHeros)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapter = PersonAdapter(list, this)
        recyclerView.adapter = adapter

        timeStamp = Date().time

//        val timeStr = String.format(timeStamp.toString())


        stringToHash = timeStamp.toString() + BuildConfig.MARVEL_PRIVATE_KEY + BuildConfig.MARVEL_PUBLIC_KEY
        hash = ConvertMD5().md5(stringToHash)

        Log.d("TIME", hash)
        load()
    }

    fun load(){
        val request = JsonObjectRequest(Request.Method.GET,     BASIC_URL + "ts=" + timeStamp + "&apikey="+ BuildConfig.MARVEL_PUBLIC_KEY +"&hash=" + hash,
            null,
            Response.Listener<JSONObject> {

                list.clear()
                val data = it.optJSONObject("data")
                if (data.length() > 0){
                    val results = data.getJSONArray("results")
                    for (i in 0 until  results.length()) {
                        val item = results.getJSONObject(i)
                        val thumbnail = item.getJSONObject("thumbnail")
                        val path = thumbnail.getString("path")
                        val extension = thumbnail.getString("extension")
                        val thumbnailEntity = Thumbnail(path,extension)
                        list.add(
                            Person(
                                item.getInt("id"),
                                item.getString("name"),
                                item.getString("description"),
                                thumbnailEntity
                            )
                        )
                    }
                }

                adapter.notifyDataSetChanged()

            }, null)

        requestQueue.add(request)
    }
}
