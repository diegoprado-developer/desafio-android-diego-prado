package com.diegoprado.desafio_android_diego_prado

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    var teste = Uri.parse("https://pbs.twimg.com/media/Dw_sw_FX0AQNi4h.jpg")
    var list: MutableList<Person> = emptyList<Person>().toMutableList()
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rvPersonsHeros)

        list.add(Person(1, "MADERA", "MADERANTE MADERA", teste))

        loadRecycle()
    }

    fun loadRecycle(){
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapter = PersonAdapter(list, this)
        recyclerView.adapter = adapter
    }
}
