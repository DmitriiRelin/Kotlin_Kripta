package com.example.kotlin_kripta

import android.os.Bundle
import android.os.ResultReceiver
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.kotlin_kripta.adapters.CoinInfoAdapter
import com.example.kotlin_kripta.databinding.ActivityCpoinPriceListBinding
import com.example.kotlin_kripta.pojo.CoinPriceInfo


class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCpoinPriceListBinding
    private lateinit var viewModel: CoinViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCpoinPriceListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val adapterListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinPriceInfo: CoinPriceInfo) {
                Log.d("TESTTEST", coinPriceInfo.fromSymbol)
            }

        }

        val adapter = CoinInfoAdapter(this, adapterListener)
        binding.rvCoinPriceList.adapter = adapter

        viewModel = ViewModelProviders.of(this).get(CoinViewModel::class.java)
        viewModel.priceList.observe(this, Observer {
            adapter.coinInfoList = it
        })
    }
}


