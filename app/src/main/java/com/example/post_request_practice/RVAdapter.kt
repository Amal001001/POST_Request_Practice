package com.example.post_request_practice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.post_request_practice.databinding.ItemRowBinding

class RVAdapter (private var users: ArrayList<user>): RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {
    class ItemViewHolder(val binding: ItemRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val user = users[position]

        holder.binding.apply {
            tvPk.text = user.pk.toString()
            tvName.text = user.name
            tvLocation.text = user.location
        }
    }

    override fun getItemCount() = users.size

    fun update(users: ArrayList<user>){
        this.users = users
        notifyDataSetChanged()
    }
}