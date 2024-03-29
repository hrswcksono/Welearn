package com.tugasakhir.welearn.presentation.view.score.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.databinding.ItemUserJoinBinding
import com.tugasakhir.welearn.domain.entity.UserJoinMulti

class JoinedGameAdapter : RecyclerView.Adapter<JoinedGameAdapter.ListViewHolder>() {

    private var listData = ArrayList<UserJoinMulti>()
    var onItemClick: ((UserJoinMulti) -> Unit)? = null

    fun setData(newListData: List<UserJoinMulti>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemUserJoinBinding.bind(itemView)
        fun bind(data: UserJoinMulti) {
            with(binding) {
                idJoin.text = data.id
                usernameTextView.text = data.username
                mode.text = data.jenisSoal.substringAfterLast(" ")
                level.text = data.idLevel.toString()
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[absoluteAdapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user_join, parent, false))

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount() = listData.size
}