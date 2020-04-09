package com.diegoprado.desafio_android_diego_prado.ui.activity

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.ActionMode
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.diegoprado.desafio_android_diego_prado.R
import com.diegoprado.desafio_android_diego_prado.data.model.Results
import com.diegoprado.desafio_android_diego_prado.ui.IResponse
import com.diegoprado.desafio_android_diego_prado.ui.viewmodel.PersonViewModel
import com.squareup.picasso.Picasso

class DetailPersonActivity : AppCompatActivity(),
    IResponse {

    lateinit var textName: TextView
    lateinit var textDescription: TextView
    lateinit var imgPerson: ImageView
    lateinit var btnComic: Button
    private lateinit var viewModel: PersonViewModel
    private var itemPosition: Int? = null
    lateinit var flipper: ViewFlipper
    lateinit var person: Results

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_person)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        textName = findViewById(R.id.txtNameDetail)
        textDescription = findViewById(R.id.txtDetailInfo)
        imgPerson = findViewById(R.id.ivPersonDetail)
        btnComic = findViewById(R.id.btnCuriosidade)
        flipper = findViewById(R.id.vp)

        val bundle: Bundle? = intent.extras
        itemPosition = bundle?.get("positionItem").toString().toInt()

        viewModel = ViewModelProviders.of(this).get(PersonViewModel::class.java)

        viewModel.personData.observe(this, Observer<ArrayList<Results>>{
            person = it.get(itemPosition!!)
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

        viewModel.createAdapter(this@DetailPersonActivity)

        btnComic.setOnClickListener{
            val intent = Intent(this@DetailPersonActivity, ComicsActivity::class.java)
            intent.putExtra("heroId", person.id)
            val activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(this@DetailPersonActivity, R.anim.fade_out, R.anim.mover_esquerda)
            ActivityCompat.startActivity(this@DetailPersonActivity, intent, activityOptionsCompat.toBundle())
        }
    }

    override fun onActionModeFinished(mode: ActionMode?) {
        super.onActionModeFinished(mode)
        overridePendingTransition(R.anim.mover_direita, R.anim.fade_in)
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