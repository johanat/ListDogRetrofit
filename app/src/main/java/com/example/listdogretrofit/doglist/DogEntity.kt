package com.example.listdogretrofit.doglist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "Dog")
data class DogEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val url: String,
    val name: String = "Indefinido"
)
