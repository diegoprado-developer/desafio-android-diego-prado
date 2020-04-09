package com.diegoprado.desafio_android_diego_prado.ui.activity

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.diegoprado.desafio_android_diego_prado.R
import com.diegoprado.desafio_android_diego_prado.data.model.ResultComics
import com.diegoprado.desafio_android_diego_prado.ui.IResponse
import com.diegoprado.desafio_android_diego_prado.ui.viewmodel.ComicsViewModel
import com.squareup.picasso.Picasso

class ComicsActivity : AppCompatActivity(),
    IResponse {

    var heroId: Int = 0
    private lateinit var viewModel: ComicsViewModel
    private lateinit var textNameComic: TextView
    private lateinit var textDescriptonComic: TextView
    private lateinit var textPriceComic: TextView
    private lateinit var imgComic: ImageView
    lateinit var flipper: ViewFlipper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comics)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        textNameComic = findViewById(R.id.tvNameComic)
        textDescriptonComic = findViewById(R.id.tvComicInfo)
        textPriceComic = findViewById(R.id.tvPrice)
        imgComic = findViewById(R.id.ivComic)
        flipper = findViewById(R.id.vpComics)


        val bundle: Bundle? = intent.extras
        heroId = bundle?.get("heroId").toString().toInt()


        viewModel = ViewModelProviders.of(this).get(ComicsViewModel::class.java)

        viewModel.comicData.observe(this, Observer<ResultComics>{
            it?.let { comic ->

                val price = comic.prices?.get(0)?.price.toString()

                if (comic.description.isNotEmpty() && price.isNotEmpty()){
                    textNameComic.text = comic.title
                    textDescriptonComic.text = comic.description
                    textPriceComic.text = price
                    val url = comic.thumbnail.path + "." + comic.thumbnail.extension
                    Picasso.get().load(url).into(imgComic)
                    this.flipper.displayedChild = 1

                } else if (comic.description.isNullOrEmpty() && price.isNullOrEmpty()){
                    textNameComic.text = comic.title
                    textDescriptonComic.text = "Comics does not contain description"
                    textPriceComic.text = "Not Price"
                    val url = comic.thumbnail.path + "." + comic.thumbnail.extension
                    Picasso.get().load(url).into(imgComic)
                    this.flipper.displayedChild = 1

                }
            }
        })

        viewModel.getComicsData(heroId, this@ComicsActivity)

    }

    override fun OnSuccess() {
        TODO("Not yet implemented")
    }

    override fun OnError(excecao: String) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage(excecao)
            .setCancelable(false)
            .setPositiveButton("Finalizar", DialogInterface.OnClickListener {
                    dialog, id -> finish()
            })

        val alert = dialogBuilder.create()
        alert.setTitle("Falha")
        alert.show()
    }
}
