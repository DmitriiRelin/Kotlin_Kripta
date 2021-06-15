package com.example.kotlin_kripta

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.kotlin_kripta.DataBase.AppDatabase
import com.example.kotlin_kripta.api.ApiFactory
import com.example.kotlin_kripta.pojo.CoinPriceInfo
import com.example.kotlin_kripta.pojo.CoinPriceInfoRawData
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()

    val priceList = db.coinPriceInfoDao().getPriceList()

    fun loadData() {
        // Старт загрузки самых популярных валют
        val disposable = ApiFactory.apiService.getTopCoinsInfo()
            // Преобразование данных в одну строку через ","
            .map { it.data?.map { it.coinInfo?.name }?.joinToString(",") }
            // Передает полученну строку внуторь блока, в виде переменной it
            .flatMap { ApiFactory.apiService.getFullPriceList(fSyms = it) }
            .map { getPriceListFromRawData(it) }
            .subscribeOn(Schedulers.io())
            .subscribe({
                db.coinPriceInfoDao().insertPriceList(it)
                Log.d("TEST_OF_LOADING_DATA", "Success: " + it.toString())
            }, {
                Log.d("TEST_OF_LOADING_DATA", "Failure: " + it.message.toString())
            })
    }

    private fun getPriceListFromRawData(coinPriceInfoRawData: CoinPriceInfoRawData): List<CoinPriceInfo> {
        val result = ArrayList<CoinPriceInfo>()
        val jsonObject = coinPriceInfoRawData.coinPriceInfoJsonObject
        if (jsonObject == null) return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinPriceInfo::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}