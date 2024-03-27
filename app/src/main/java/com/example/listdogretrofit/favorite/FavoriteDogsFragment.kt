package com.example.listdogretrofit.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listdogretrofit.MainActivity
import com.example.listdogretrofit.databinding.FragmentFavoriteDogsBinding
import com.example.listdogretrofit.doglist.AppDatabase
import com.example.listdogretrofit.doglist.DogAdapter
import com.example.listdogretrofit.doglist.DogEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FavoriteDogsFragment : Fragment() {

    lateinit var db : AppDatabase
    lateinit var  binding: FragmentFavoriteDogsBinding
    private lateinit var adapter: FavoriteDogsAdapter
    private val favoriteDog = mutableListOf<DogEntity>()

    private fun initRecyclerView() {

        adapter = FavoriteDogsAdapter(favoriteDog)
        binding.recyclerDogs.layoutManager = LinearLayoutManager(context)
        binding.recyclerDogs.adapter = adapter

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteDogsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = (activity as MainActivity).db
        initRecyclerView()
        readFavoriteDogs()
        adapter.onBtmDelete = {id, position ->
            CoroutineScope(Dispatchers.IO).launch {
                db.dogDao().delete(DogEntity(id,""))
                favoriteDog.removeAt(position)
                withContext(Dispatchers.Main){
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }
    private fun readFavoriteDogs(){
      CoroutineScope(Dispatchers.IO).launch {
         val list = db.dogDao().getAll()
          list.forEach {
              favoriteDog.add(it)
          }
      }
    }
}