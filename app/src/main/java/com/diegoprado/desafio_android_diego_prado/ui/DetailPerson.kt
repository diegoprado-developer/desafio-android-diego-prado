package com.diegoprado.desafio_android_diego_prado.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.diegoprado.desafio_android_diego_prado.R
import com.diegoprado.desafio_android_diego_prado.data.model.Results
import com.diegoprado.desafio_android_diego_prado.ui.viewmodel.PersonViewModel
import com.squareup.picasso.Picasso

class DetailPerson : AppCompatActivity() {

    lateinit var textName: TextView
    lateinit var textDescription: TextView
    lateinit var imgPerson: ImageView
    private lateinit var viewModel: PersonViewModel
    private var itemPosition: Int? = null
    lateinit var flipper: ViewFlipper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_person)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        textName = findViewById(R.id.txtNameDetail)
        textDescription = findViewById(R.id.txtDetailInfo)
        imgPerson = findViewById(R.id.ivPersonDetail)
        flipper = findViewById(R.id.vp)

        this.flipper.displayedChild = 0

        val bundle: Bundle? = intent.extras
        itemPosition = bundle?.get("positionItem").toString().toInt()

        viewModel = ViewModelProviders.of(this).get(PersonViewModel::class.java)

        viewModel.personData.observe(this, Observer<ArrayList<Results>>{
            val person = it.get(itemPosition!!)
            val url = person.thumbnail.path + "." + person.thumbnail.extension
            if (person.name.isNotEmpty() && person.description.isNotEmpty() && url.isNotEmpty()){
                textName.text = person.name
                textDescription.text = person.description
                Picasso.get().load(url).into(imgPerson)
                this.flipper.displayedChild = 1
            }else if (person.description.isNullOrEmpty() && person.name.isNotEmpty() && url.isNotEmpty()){
                textName.text = person.name
                textDescription.text = "Character does not contain description"
                Picasso.get().load(url).into(imgPerson)
                this.flipper.displayedChild = 1
            }

        })

        viewModel.createAdapter()
    }
}