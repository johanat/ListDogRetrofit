package com.example.listdogretrofit.doglist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.listdogretrofit.R
import com.squareup.picasso.Picasso

class DogAdapter(private val favoriteDog: MutableList<DogEntity>, private val dogInfos: List<DogInfo>) :
    RecyclerView.Adapter<DogViewHolder>() {


    var onFavClicked: (String, Int)->Unit = {_, _ ->}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DogViewHolder( layoutInflater.inflate(R.layout.item_dog, parent, false))
    }
    override fun getItemCount(): Int {
        return dogInfos.size
    }
    // se llama por cada  item  que vas a pintar
    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {

        val dogInfo = dogInfos[position]

        /*  VER UNA CONDION QUE COMPARE UNA LISTA A LA OTRA Y PONER el icono adecuado*/
        holder.binding.ivFavorite.setImageResource(R.drawable.ic_no_favorite)

        for (dog in favoriteDog) {
            if (dog.url == dogInfo.url) {
                holder.binding.ivFavorite.setImageResource(R.drawable.ic_favorite)
            }
        }


        holder.binding.ivFavorite.setOnClickListener {
            onFavClicked.invoke(dogInfo.url, position)
            favoriteDog.add(DogEntity(position,dogInfo.url))
            notifyItemChanged(position)

            Toast.makeText(holder.binding.root.context,"$position",Toast.LENGTH_SHORT).show()
        }
        holder.binding.llContainer.visibility = View.GONE
        holder.binding.delete.visibility = View.GONE
        holder.binding.tvName.visibility = View.GONE
        Picasso.get().load(dogInfo.url)
            .into(holder.binding.ivDog) //carga las imagenes de internet y las muestra en nuestra app
    }

}