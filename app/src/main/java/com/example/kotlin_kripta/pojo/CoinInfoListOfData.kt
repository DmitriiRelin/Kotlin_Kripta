package com.example.kotlin_kripta.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// ПРИХОДИТ СПИСОК ВАЛЮТ В ВИДЕ ЛИСТА
// ПО КЛЮЧУ DATA придет массив Datum

data class CoinInfoListOfData(
        @SerializedName("Data")
        @Expose
        val data: List<Datum>? = null
)