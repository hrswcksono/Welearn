package com.tugasakhir.welearn.presentation.view.huruf.singleplayer.level

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.databinding.GridLevelHurufBinding
import com.tugasakhir.welearn.domain.entity.LevelEntity

class ListLevelHurufAdapter : RecyclerView.Adapter<ListLevelHurufAdapter.GridViewHolder>() {

    private var listData = ArrayList<LevelEntity>()
    var onItemClick: ((LevelEntity) -> Unit)? = null

    fun setData(newListData: List<LevelEntity>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    inner class GridViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = GridLevelHurufBinding.bind(itemView)
        fun bind(data: LevelEntity) {
            with(binding) {
                idJenisGrid.text = data.idLevel.toString()
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