package com.tugasakhir.welearn.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.databinding.ItemUserParticipantBinding
import com.tugasakhir.welearn.domain.entity.UserPaticipantEntity

class UserParticipantAdapter : RecyclerView.Adapter<UserParticipantAdapter.ListViewHolder>() {

    private var listData = ArrayList<UserPaticipantEntity>()

    fun setData(newListData: List<UserPaticipantEntity>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemUserParticipantBinding.bind(itemView)
        fun bind(data: UserPaticipantEntity) {
            with(binding) {
                tvUsername.text = data.username
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user_participant, parent, false))

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount() = listData.size
}