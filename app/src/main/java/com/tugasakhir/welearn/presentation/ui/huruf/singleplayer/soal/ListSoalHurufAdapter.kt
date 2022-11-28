package com.tugasakhir.welearn.presentation.ui.huruf.singleplayer.soal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.databinding.GridSoalHurufBinding
import com.tugasakhir.welearn.domain.entity.SoalEntity

class ListSoalHurufAdapter : RecyclerView.Adapter<ListSoalHurufAdapter.GridViewHolder>() {

    private var listData = ArrayList<SoalEntity>()
    var onItemClick: ((SoalEntity) -> Unit)? = null

    fun setData(newListData: List<SoalEntity>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    inner class GridViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = GridSoalHurufBinding.bind(itemView)
        fun bind(data: SoalEntity) {
            with(binding) {
                idSoalHuruf.text = (absoluteAdapterPosition+1).toString()
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
    ) = GridViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.grid_soal_huruf, parent, false))

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount() = listData.size
}