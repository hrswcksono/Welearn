package com.tugasakhir.welearn.presentation.ui.score

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.databinding.ItemScoreAngkaBinding
import com.tugasakhir.welearn.domain.model.UserScore

class ScoreAngkaAdapter : RecyclerView.Adapter<ScoreAngkaAdapter.ListViewHolder>() {

    private var listData = ArrayList<UserScore>()
    var onItemClick: ((UserScore) -> Unit)? = null

    fun setData(newListData: List<UserScore>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemScoreAngkaBinding.bind(itemView)
        fun bind(data: UserScore) {
            with(binding) {
                tvNoAngka.text = (absoluteAdapterPosition+1).toString()
                tvNamaSkor.text = data.name
                tvValueSkorAngka.text = data.total
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_score_angka, parent, false))

    override fun onBindViewHolder(holder: ScoreAngkaAdapter.ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount() = listData.size
}