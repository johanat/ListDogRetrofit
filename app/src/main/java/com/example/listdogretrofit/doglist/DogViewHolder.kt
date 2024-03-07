package com.example.listdogretrofit.doglist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.listdogretrofit.databinding.ItemDogBinding
import com.squareup.picasso.Picasso

class DogViewHolder(view:View):RecyclerView.ViewHolder(view) {
    val binding = ItemDogBinding.bind(view)
}