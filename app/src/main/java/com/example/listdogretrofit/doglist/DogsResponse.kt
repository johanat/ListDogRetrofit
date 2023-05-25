package com.example.listdogretrofit.doglist

import com.google.gson.annotations.SerializedName
// es a lo que se va parsear el gson

data class DogsResponse(var status:String,
                        @SerializedName("message") var images:List<String>
                        )
