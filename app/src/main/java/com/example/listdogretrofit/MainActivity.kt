package com.example.listdogretrofit

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.listdogretrofit.databinding.ActivityMainBinding
import com.example.listdogretrofit.doglist.APIService
import com.example.listdogretrofit.doglist.AppDatabase
import com.example.listdogretrofit.doglist.DogAdapter
import com.example.listdogretrofit.doglist.DogEntity
import com.example.listdogretrofit.doglist.DogsResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class MainActivity : AppCompatActivity() {
    lateinit var db : AppDatabase
    lateinit var navController : NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         db = createDb()
        /*binding sirve para agrupar todos las referencias de los views,
        en un solo objeto, se los puede utilizar en cualquier clase que tengas layout,
          y hay que activar un binding en el proyecto (eso se lo busca en intener como implementarlo)*/

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController

    }
    private fun createDb(): AppDatabase{

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()
        return db
    }


    /*corrutinas el uso principal es ejecutar de forma asincronas,( que no lo hace a la misma vez,
    en tiempo de ejecucion se hace en un hilo secundario que seria la llamada a internet*/
    /*CORRUTINA */


}