package com.skinconnect.userapps.ui.main.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.skinconnect.userapps.data.entity.ProfileTodoItem
import com.skinconnect.userapps.databinding.ItemTodoBinding

class ProfileAdapter :
    ListAdapter<ProfileTodoItem, ProfileAdapter.ProfileViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTodoBinding.inflate(layoutInflater, parent, false)
        return ProfileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val story = getItem(position)
        holder.bind(story)
    }

    class ProfileViewHolder(private val binding: ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProfileTodoItem) {
            binding.actionDesc.text = item.scheduleTitle
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProfileTodoItem>() {
            override fun areItemsTheSame(oldItem: ProfileTodoItem, newItem: ProfileTodoItem) =
                oldItem.scheduleId == newItem.scheduleId

            override fun areContentsTheSame(oldItem: ProfileTodoItem, newItem: ProfileTodoItem) =
                oldItem.scheduleTitle == newItem.scheduleTitle
        }
    }
}