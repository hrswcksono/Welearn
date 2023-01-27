package com.tugasakhir.welearn.presentation.view.score.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.databinding.ItemScoreSingleBinding
import com.tugasakhir.welearn.domain.entity.RankingSingleScore

class ScoreHurufAdapter: RecyclerView.Adapter<ScoreHurufAdapter.ListViewHolder>() {

    private var listData = ArrayList<RankingSingleScore>()
    var onItemClick: ((RankingSingleScore) -> Unit)? = null

    fun setData(newListData: List<RankingSingleScore>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemScoreSingleBinding.bind(itemView)
        fun bind(data: RankingSingleScore) {
            with(binding) {
                tvNoSingle.text = (absoluteAdapterPosition+1).toString()
                tvNamaSingle.text = data.name
                tvSkorSingle.text = data.total
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_score_single, parent, false))

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount() = listData.size

}