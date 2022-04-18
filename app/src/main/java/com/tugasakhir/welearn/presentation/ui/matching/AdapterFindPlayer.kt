package com.tugasakhir.welearn.presentation.ui.matching

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.databinding.ItemListPlayerBinding

class AdapterFindPlayer : RecyclerView.Adapter<AdapterFindPlayer.ListViewHolder>() {

    private var listData = ArrayList<String>()
    var onItemClick:((String) -> Unit)?=null

    fun setData(newListData: List<String>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListPlayerBinding.bind(itemView)
        fun bind(data: String) {

        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[absoluteAdapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(
        parent.context
    ).inflate(R.layout.item_list_player, parent, false))

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount()= listData.size
}