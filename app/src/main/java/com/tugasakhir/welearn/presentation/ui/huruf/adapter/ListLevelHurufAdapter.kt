package com.tugasakhir.welearn.presentation.ui.huruf.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.databinding.GridLevelHurufBinding
import com.tugasakhir.welearn.domain.model.Level

class ListLevelHurufAdapter : RecyclerView.Adapter<ListLevelHurufAdapter.GridViewHolder>() {

    private var listData = ArrayList<Level>()
    var onItemClick: ((Level) -> Unit)? = null

    fun setData(newListData: List<Level>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    inner class GridViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = GridLevelHurufBinding.bind(itemView)
        fun bind(data: Level) {
            with(binding) {
                idJenisGrid.text = data.id_level.toString()
                jenisSoal.text = data.level_soal
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[absoluteAdapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = GridViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.grid_level_huruf, parent, false))

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount() = listData.size
}