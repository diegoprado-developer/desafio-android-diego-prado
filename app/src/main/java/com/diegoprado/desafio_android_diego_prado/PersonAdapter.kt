package com.diegoprado.desafio_android_diego_prado

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class PersonAdapter(val list: List<Person>, val context: Context): RecyclerView.Adapter<PersonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_person, parent, false)

        return ViewHolder(view  )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = list[position]
        holder.apply {
            name.text = person.name
            val url = person.thumbnail.path + "." + person.thumbnail.extension
            Picasso.get().load(url).into(holder.thumbnail)
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.tvPerson)
        val thumbnail: ImageView = itemView.findViewById(R.id.ivPerson)
        val infoDetail: Button = itemView.findViewById(R.id.btnInfo)
    }


}