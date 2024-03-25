package com.example.listdogretrofit.doglist

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface DogDao {
    @Query("SELECT * FROM Dog")
    fun getAll(): List<DogEntity>

    @Insert
    fun insertAll(vararg dogs: DogEntity)

    @Delete
    fun delete(dog: DogEntity)
}