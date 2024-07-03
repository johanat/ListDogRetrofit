package com.example.listdogretrofit

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listdogretrofit.databinding.FragmentDogsFramentBinding
import com.example.listdogretrofit.doglist.APIService
import com.example.listdogretrofit.doglist.AppDatabase
import com.example.listdogretrofit.doglist.DogAdapter
import com.example.listdogretrofit.doglist.DogEntity
import com.example.listdogretrofit.doglist.DogInfo
import com.example.listdogretrofit.doglist.DogsResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DogsFragment : Fragment(), androidx.appcompat.widget.SearchView.OnQueryTextListener{

    lateinit var adapter: DogAdapter
    private val dogImages = mutableListOf<DogInfo>()
    lateinit var  binding: FragmentDogsFramentBinding
    lateinit var db : AppDatabase
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    private val favoriteDog = mutableListOf<DogEntity>()


    private fun initRecyclerView() {
        adapter = DogAdapter(favoriteDog, dogImages)
        binding.recyclerDogs.layoutManager = LinearLayoutManager(context)
        binding.recyclerDogs.adapter = this.adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDogsFramentBinding.inflate(inflater, container, false)
        sharedPreferences =
            requireContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        val lastBreed = sharedPreferences.getString("raza", "")
        binding.searchDogs.setQuery(lastBreed, true)
        if (!lastBreed.isNullOrEmpty()) {
            searchByName(lastBreed)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = (activity as MainActivity).db
        binding.searchDogs.setOnQueryTextListener(this)
        initRecyclerView()
        readFavoriteDogs()

        adapter.onFavClicked = { url, _ ->
            CoroutineScope(Dispatchers.IO).launch {
                db.dogDao().insertAll(DogEntity(null, url))
            }
        }

        binding.btnFlotating.setOnClickListener {
            val  activity =  activity as MainActivity
            activity.navController.navigate(R.id.action_dogsFrament_to_favoriteDogsFragment)
            initRecyclerView()
        }

    }
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create()) // esta linea añade el conversor de gson a objetos kotlin
            .build()
    }
    private fun searchByName(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val retrofit = getRetrofit()

            val service =
                retrofit.create(APIService::class.java) // esta linea nos indica como se llama a la Api

            val call = service.getDogsByBreeds("$query/images")
            val puppies: DogsResponse? = call.body() // puppies es la representacion de json que te devuelve la Api
            // cambiar  de corrutinas o al hilo principal llamando a runOnUiThread
            if (call.isSuccessful) {
                //show recycleView
                val images = puppies?.images
                    ?: emptyList() // ?: si lo de atras que me han puesto es nulo llamo a la funcion emptyList
                dogImages.clear()
                images.forEach {
                    val dogInfo = DogInfo()
                    dogInfo.url = it
                    dogInfo.favorite = isFavorite(dogInfo.url)
                    dogImages.add(dogInfo)

                }
                withContext(Dispatchers.Main) {
                    adapter.notifyDataSetChanged() // esta linea indica al adapter que hubo cambios
                }

            } else {
                withContext(Dispatchers.Main) {
                    showError()
                }
            }
            withContext(Dispatchers.Main) {
                hideKeyboard()
            }
        }
    }

private suspend fun isFavorite(url: String): Boolean {
    return true
    val favorites = db.dogDao().getFavorite(url)
    if (favorites.isEmpty()) {
        return false
    } else {
        return true
        }

}

private fun readFavoriteDogs() {
    CoroutineScope(Dispatchers.IO).launch {
        val list = db.dogDao().getAll()
        favoriteDog.clear()
        list.forEach {
            favoriteDog.add(it)
            withContext(Dispatchers.Main) {
                adapter.notifyDataSetChanged()
            }
        }
    }
}

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()) {
            //aqui debo poner sheredpreferences

            editor.putString("raza", query)
            editor.apply()
            searchByName(query.trim().toLowerCase())
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
       return true
    }

    private fun hideKeyboard() {
        val imm =  (activity as MainActivity).getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE)  as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
    }

    private fun showError() {
        Toast.makeText(context, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }
}