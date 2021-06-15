package com.example.kotlin_kripta.adapters

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_kripta.R
import com.example.kotlin_kripta.databinding.ItemCoinInfoBinding
import com.example.kotlin_kripta.pojo.CoinPriceInfo
import com.squareup.picasso.Picasso

class CoinInfoViewHolder(itemView: View, private val context: Context, private val onItemClickListener: CoinInfoAdapter.OnCoinClickListener) : RecyclerView.ViewHolder(itemView) {

    private val binding: ItemCoinInfoBinding = ItemCoinInfoBinding.bind(itemView)

    fun bind(coinPriceInfo: CoinPriceInfo, context: Context) {
        with(coinPriceInfo) {
            val symbolsTemplate = context.resources.getString(R.string.symbols_template)
            val lastUpdateTemplate = context.resources.getString(R.string.last_update_template)
            binding.tvSymbols.text = String.format(symbolsTemplate, fromSymbol, toSymbol)
            binding.tvPrice.text = price
            binding.tvLastUpdate.text = String.format(lastUpdateTemplate, getFormattedTime())
            Picasso.get().load(getFullImageUrl()).into(binding.ivLogoCoin)
            itemView.setOnClickListener {
                onItemClickListener.onCoinClick(this)
            }
        }
    }

}