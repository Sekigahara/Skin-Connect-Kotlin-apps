package com.skinconnect.userapps.ui.main.profile

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.skinconnect.userapps.data.entity.ProfileTodoItem
import com.skinconnect.userapps.databinding.ItemTodoBinding

class ProfileAdapter : ListAdapter<ProfileTodoItem, ProfileAdapter.ProfileViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTodoBinding.inflate(layoutInflater, parent, false)
        return ProfileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val story = getItem(position)
        holder.bind(story)
    }

    class ProfileViewHolder(private val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProfileTodoItem) {
            binding.actionDesc.text = item.scheduleTitle
//            val requestOptions = RequestOptions.placeholderOf(R.drawable.ic_loading)
//                .error(R.drawable.ic_error)
//
//            itemView.setOnClickListener {
//                val intent = Intent(itemView.context, StoryDetailActivity::class.java)
//                intent.putExtra("Story", item)
//
//                val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                    itemView.context as Activity,
//                    Pair(binding.imageViewStory, "profile"),
//                    Pair(binding.textViewStoryName, "name"),
//                    Pair(binding.textViewStoryBody, "story")
//                )
//
//                itemView.context.startActivity(intent, optionsCompat.toBundle())
//            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProfileTodoItem>() {
            override fun areItemsTheSame(oldItem: ProfileTodoItem, newItem: ProfileTodoItem) = oldItem.scheduleId == newItem.scheduleId

            override fun areContentsTheSame(oldItem: ProfileTodoItem, newItem: ProfileTodoItem) =
                oldItem.scheduleTitle == newItem.scheduleTitle
        }
    }
}