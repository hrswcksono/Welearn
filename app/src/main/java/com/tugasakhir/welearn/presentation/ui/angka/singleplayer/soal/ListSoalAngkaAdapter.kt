package com.tugasakhir.welearn.presentation.ui.angka.singleplayer.soal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.databinding.GridSoalAngkaBinding
import com.tugasakhir.welearn.domain.model.Soal

class ListSoalAngkaAdapter : RecyclerView.Adapter<ListSoalAngkaAdapter.GridViewHolder>() {

    private var listData = ArrayList<Soal>()
    var onItemClick: ((Soal) -> Unit)? = null

    fun setData(newListData: List<Soal>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    inner class GridViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = GridSoalAngkaBinding.bind(itemView)
        var index: Int = 0
        fun bind(data: Soal) {
            with(binding) {
                idSoalAngka.text = (absoluteAdapterPosition+1).toString()
                index = absoluteAdapterPosition +1
                soalAngka.text = "Soal $index"
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
    ) = GridViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.grid_soal_angka, parent, false))

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount() = listData.size
}