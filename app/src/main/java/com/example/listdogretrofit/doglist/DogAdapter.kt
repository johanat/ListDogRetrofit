package com.example.listdogretrofit.doglist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listdogretrofit.R
import com.squareup.picasso.Picasso

    class DogAdapter(private val images: List<String>) : RecyclerView.Adapter<DogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DogViewHolder(layoutInflater.inflate(R.layout.item_dog, parent, false))

    }

    override fun getItemCount(): Int {
        return images.size
    }

    // se llama por cada  item  que vas a pintar
    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        val item = images[position]
        // holder tiene los view que va a pintar
        // biding tengo todo los views
        holder.bind(item)
        //holder.binding.ivDog
    }
}