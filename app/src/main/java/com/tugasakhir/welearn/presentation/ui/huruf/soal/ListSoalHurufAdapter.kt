package com.tugasakhir.welearn.presentation.ui.huruf.soal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.databinding.GridSoalHurufBinding
import com.tugasakhir.welearn.domain.model.Level
import com.tugasakhir.welearn.domain.model.Soal

class ListSoalHurufAdapter : RecyclerView.Adapter<ListSoalHurufAdapter.GridViewHolder>() {

    private var listData = ArrayList<Soal>()
    var onItemClick: ((Soal) -> Unit)? = null

    fun setData(newListData: List<Soal>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    inner class GridViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = GridSoalHurufBinding.bind(itemView)
        var index: Int = 0
        fun bind(data: Soal) {
            with(binding) {
                idSoalHuruf.text = (absoluteAdapterPosition+1).toString()
                index = absoluteAdapterPosition +1
                soalHuruf.text = "Soal $index"
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