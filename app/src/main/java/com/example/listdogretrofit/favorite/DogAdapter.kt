package com.example.listdogretrofit.favorite

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.listdogretrofit.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.NonDisposableHandle.parent

class DogAdapter(private val images: List<String>) : RecyclerView.Adapter<DogViewHolder>() {

    var suma: (Int, Int)->Unit = { _, _ -> }
    var onFavClicked: (String, Int)->Unit = {_, _ ->}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DogViewHolder( layoutInflater.inflate(R.layout.item_dog, parent, false))
    }

    override fun getItemCount(): Int {
        return images.size
    }

    // se llama por cada  item  que vas a pintar
    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {

        val url = images[position]
        holder.binding.ivFavorite.setOnClickListener {
            onFavClicked.invoke(url, position)
            Toast.makeText(holder.binding.root.context,"$position",Toast.LENGTH_SHORT).show()
        }

        Picasso.get().load(url).into(holder.binding.ivDog) //carga las imagenes de internet y las muestra en nuestra app
    }
}