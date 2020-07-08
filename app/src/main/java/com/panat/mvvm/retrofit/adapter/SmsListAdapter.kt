package com.panat.mvvm.retrofit.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.panat.mvvm.retrofit.databinding.ItemEventsBinding
import com.panat.mvvm.retrofit.model.SmsItems

class SmsListAdapter(private val context: Context) :
    RecyclerView.Adapter<SmsListAdapter.SmsListViewHolder>() {

    private var items: List<SmsItems> = mutableListOf()

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmsListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemEventsBinding.inflate(inflater, parent, false)
        return SmsListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SmsListViewHolder, position: Int) =
        holder.bind(items[position])

    class SmsListViewHolder(private val binding: ItemEventsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val context: Context = binding.root.context

        @SuppressLint("SetTextI18n")
        fun bind(item: SmsItems) {
            binding.name.text = "From : " + item.phone
            binding.event.text = item.text
        }
    }

    fun loadData(data: List<SmsItems>) {
        this.items = data
        notifyDataSetChanged()
    }
}
