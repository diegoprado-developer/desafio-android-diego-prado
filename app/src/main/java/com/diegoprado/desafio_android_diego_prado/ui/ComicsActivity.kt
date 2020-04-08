package com.diegoprado.desafio_android_diego_prado.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.diegoprado.desafio_android_diego_prado.R
import com.diegoprado.desafio_android_diego_prado.data.model.ResultComics
import com.diegoprado.desafio_android_diego_prado.data.model.Results
import com.diegoprado.desafio_android_diego_prado.ui.adapter.PersonAdapter
import com.diegoprado.desafio_android_diego_prado.ui.viewmodel.ComicsViewModel
import com.diegoprado.desafio_android_diego_prado.ui.viewmodel.PersonViewModel

class CuriosidadeComics : AppCompatActivity() {

    private var heroId: Int? = null
    private lateinit var viewModel: ComicsViewModel
    private var txt

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_curiosidade_comics)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle: Bundle? = intent.extras
        heroId = bundle?.get("heroId").toString().toInt()


        viewModel = ViewModelProviders.of(this).get(ComicsViewModel::class.java)

        viewModel.comicData.observe(this, Observer<ResultComics>{
            it?.let {

            }
        })

        viewModel.getComisData(heroId!!)


    }
}
