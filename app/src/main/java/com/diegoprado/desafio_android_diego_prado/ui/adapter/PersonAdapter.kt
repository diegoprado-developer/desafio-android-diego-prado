package com.diegoprado.desafio_android_diego_prado.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.diegoprado.desafio_android_diego_prado.R
import com.diegoprado.desafio_android_diego_prado.data.model.Results
import com.diegoprado.desafio_android_diego_prado.ui.activity.DetailPersonActivity
import com.squareup.picasso.Picasso

class PersonAdapter(val list: List<Results>,val contex: Context): RecyclerView.Adapter<PersonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_person, parent, false)

        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = list[position]
        holder.apply {
            name.text = person.name
            val url = person.thumbnail.path + "." + person.thumbnail.extension
            Picasso.get().load(url).into(holder.thumbnail)

            loadDetail(holder, position)
        }
    }

    fun loadDetail(holder:ViewHolder, pos: Int) {
        holder.infoDetail.setOnClickListener {
            val intent = Intent(contex, DetailPersonActivity::class.java)
            intent.putExtra("positionItem", pos)
            val activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(contex, R.anim.fade_out, R.anim.mover_esquerda)
            ActivityCompat.startActivity(contex, intent, activityOptionsCompat.toBundle())
//            contex.startActivity(intent)
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.tvPerson)
        val thumbnail: ImageView = itemView.findViewById(R.id.ivPerson)
        val infoDetail: Button = itemView.findViewById(R.id.btnInfo)
    }

}