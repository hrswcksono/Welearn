package com.tugasakhir.welearn.presentation.view.angka.singleplayer.level

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.databinding.GridLevelAngkaBinding
import com.tugasakhir.welearn.domain.entity.LevelEntity

class ListLevelAngkaAdapter : RecyclerView.Adapter<ListLevelAngkaAdapter.GridViewHolder>() {

    private var listData = ArrayList<LevelEntity>()
    var onItemClick: ((LevelEntity) -> Unit)? = null

    fun setData(newListData: List<LevelEntity>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    inner class GridViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = GridLevelAngkaBinding.bind(itemView)
        fun bind(data: LevelEntity) {
            with(binding) {
                idJenisAngka.text = data.idLevel.toString()
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