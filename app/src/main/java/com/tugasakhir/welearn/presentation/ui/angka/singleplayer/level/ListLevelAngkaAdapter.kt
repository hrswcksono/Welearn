package com.tugasakhir.welearn.presentation.ui.angka.singleplayer.level

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.databinding.GridLevelAngkaBinding
import com.tugasakhir.welearn.domain.model.Level

class ListLevelAngkaAdapter : RecyclerView.Adapter<ListLevelAngkaAdapter.GridViewHolder>() {

    private var listData = ArrayList<Level>()
    var onItemClick: ((Level) -> Unit)? = null

    fun setData(newListData: List<Level>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    inner class GridViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = GridLevelAngkaBinding.bind(itemView)
        fun bind(data: Level) {
            with(binding) {
                idJenisAngka.text = data.id_level.toString()
                jenisAngka.text = data.level_soal
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
    ) = GridViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.grid_level_angka, parent, false))

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount() = listData.size
}