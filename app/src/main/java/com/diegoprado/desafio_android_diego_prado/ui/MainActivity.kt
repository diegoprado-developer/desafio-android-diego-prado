package com.diegoprado.desafio_android_diego_prado.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ViewFlipper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diegoprado.desafio_android_diego_prado.R
import com.diegoprado.desafio_android_diego_prado.data.model.Results
import com.diegoprado.desafio_android_diego_prado.ui.adapter.PersonAdapter
import com.diegoprado.desafio_android_diego_prado.ui.viewmodel.PersonViewModel

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: PersonViewModel
    lateinit var flipper: ViewFlipper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rvPersonsHeros)
        flipper = findViewById(R.id.vpmain)

        this.flipper.displayedChild = 0

        viewModel = ViewModelProviders.of(this).get(PersonViewModel::class.java)

        viewModel.personData.observe(this, Observer<ArrayList<Results>>{
            it?.let {
                val adapter = PersonAdapter(it, this@MainActivity)
                inflateList(adapter)
            }
        })

        viewModel.createAdapter()
    }

    private fun inflateList(adapter: PersonAdapter){
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        this.flipper.displayedChild = 1
    }

}
