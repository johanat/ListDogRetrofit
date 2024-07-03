package com.example.listdogretrofit

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.room.Room
import com.example.listdogretrofit.databinding.ActivityMainBinding
import com.example.listdogretrofit.doglist.AppDatabase


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
        Log.i("johana","holis estoy en onCreate")
    }
    private fun createDb(): AppDatabase{

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()
        return db
    }

    override fun onStart() {
        super.onStart()
        Log.i("johana","holis estoy en onStar")
    }

    override fun onResume() {
        super.onResume()
        Log.i("johana","holis estoy en OnResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i("Johana", "holis estoy Pause")

    }

    override fun onStop() {
        super.onStop()
        Log.i("johana","holis estoy en onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("johana","holis estoy en onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("johana","holis estoy en onStar")
    }
    


    /*corrutinas el uso principal es ejecutar de forma asincronas,( que no lo hace a la misma vez,
    en tiempo de ejecucion se hace en un hilo secundario que seria la llamada a internet*/
    /*CORRUTINA */


}