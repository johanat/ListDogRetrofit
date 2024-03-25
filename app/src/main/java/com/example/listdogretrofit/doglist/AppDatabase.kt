package com.example.listdogretrofit.doglist

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DogEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dogDao(): DogDao
}