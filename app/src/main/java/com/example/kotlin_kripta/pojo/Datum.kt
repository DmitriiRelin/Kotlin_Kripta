package com.example.kotlin_kripta.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// ОБЪЕКТЫ СОДЕРЖАТ ТОЛЬКО 1 поле
// В ЭТО ОБЪЕКТЕ СОДЕРЖИТСЯ ИНФОРМАЦИЯ О ВАЛЮТЕ

data class Datum(
        @SerializedName("CoinInfo")
        @Expose
        val coinInfo: CoinInfo? = null
)
