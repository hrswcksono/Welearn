package com.tugasakhir.welearn.presentation.view.score.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.databinding.ItemScoreMultiplayerBinding
import com.tugasakhir.welearn.domain.entity.ScoreMultiEntity

class ScoreMultiAdapter: RecyclerView.Adapter<ScoreMultiAdapter.ListViewHolder>() {

    private var listData = ArrayList<ScoreMultiEntity>()
    fun setData(newListData: List<ScoreMultiEntity>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemScoreMultiplayerBinding.bind(itemView)
        fun bind(data: ScoreMultiEntity) {
            with(binding) {
                tvNoAngka.text = (absoluteAdapterPosition+1).toString()
                tvNamaSkorMulti.text = data.name
                tvValueSkorMulti.text = data.score.toString()
                tvValueDurationMulti.text = data.duration.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_score_multiplayer, parent, false))

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount() = listData.size
}