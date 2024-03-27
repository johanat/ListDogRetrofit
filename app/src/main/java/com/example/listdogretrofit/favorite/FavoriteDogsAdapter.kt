package com.example.listdogretrofit.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.listdogretrofit.R
import com.example.listdogretrofit.doglist.DogEntity
import com.squareup.picasso.Picasso

class FavoriteDogsAdapter(private val dogEntities: List<DogEntity>) : RecyclerView.Adapter<DogViewHolder>() {

    var onFavClicked: (String, Int)->Unit = {_, _ ->}
    var onBtmDelete: (Int, Int)->Unit = { _, _ ->}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DogViewHolder( layoutInflater.inflate(R.layout.item_dog, parent, false))
    }

    override fun getItemCount(): Int {
        return dogEntities.size
    }

    // se llama por cada  item  que vas a pintar
    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {

        val dogEntity = dogEntities[position]
        holder.binding.ivFavorite.setOnClickListener {
            onFavClicked.invoke(dogEntity.url, dogEntity.id!!)
            Toast.makeText(holder.binding.root.context,"$position",Toast.LENGTH_SHORT).show()
        }
        holder.binding.delete.setOnClickListener {
            onBtmDelete.invoke(dogEntity.id!!, position)
        }

        Picasso.get().load(dogEntity.url).into(holder.binding.ivDog) //carga las imagenes de internet y las muestra en nuestra app
    }
}