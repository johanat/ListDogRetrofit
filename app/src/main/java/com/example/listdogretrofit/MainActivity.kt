package com.example.listdogretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listdogretrofit.databinding.ActivityMainBinding
import com.example.listdogretrofit.doglist.APIService
import com.example.listdogretrofit.doglist.DogAdapter
import com.example.listdogretrofit.doglist.DogsResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private lateinit var binding: ActivityMainBinding
private lateinit var adapter: DogAdapter
private val dogImage =
    mutableListOf<String>() // las imagenes deben cambiar del recycler view por eso debemos
// modificar el listado que le estamos pasando

class MainActivity : AppCompatActivity(), androidx.appcompat.widget.SearchView.OnQueryTextListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*binding sirve para agrupar todos las referencias de los views,
        en un solo objeto, se los puede utilizar en cualquier clase que tengas layout,
          y hay que activar un binding en el proyecto (eso se lo busca en intener como implementarlo)*/

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.svDogs.setOnQueryTextListener(this)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = DogAdapter(dogImage)
        binding.recyclerDogs.layoutManager = LinearLayoutManager(this)
        binding.recyclerDogs.adapter = adapter


    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create()) // esta linea añade el conversor de gson a objetos kotlin
            .build()
    }

    /*corrutinas el uso principal es ejecutar de forma asincronas,( que no lo hace a la misma vez,
    en tiempo de ejecucion se hace en un hilo secundario que seria la llamada a internet*/
    /*CORRUTINA */
    private fun searchByname(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val retrofit = getRetrofit()

            val service =
                retrofit.create(APIService::class.java) // esta linea nos indica como se llama a la Api
            val call = service.getDogsByBreeds("$query/images")
            val puppies: DogsResponse? =
                call.body() // puppies es la representacion de json que te devuelve la Api
            // como volvemos al hilo principal llamando a runOnUiThread
            runOnUiThread {
                if (call.isSuccessful) {
                    //show recycleView
                    val images = puppies?.images
                        ?: emptyList() // ?: si lo de atras que me han puesto es nulo llamo a la funcion emptyList

                    dogImage.clear()
                    dogImage.addAll(images)
                    adapter.notifyDataSetChanged() // esta linea indica al adapter que hubo cambios
                } else {
                    showError()
                }
                hideKeyboard()
            }
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
    }

    private fun showError() {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()) {
            searchByname(query.toLowerCase())
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}