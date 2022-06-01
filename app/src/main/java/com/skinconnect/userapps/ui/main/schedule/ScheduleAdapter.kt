package com.skinconnect.userapps.ui.main.schedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skinconnect.userapps.data.entity.response.ListScheduleItem
import com.skinconnect.userapps.databinding.ItemScheduleBinding

class ScheduleAdapter(private val listSchedule: ArrayList<ListScheduleItem>) : RecyclerView.Adapter<ScheduleAdapter.ListViewHolder>() {
    inner class ListViewHolder(var binding: ItemScheduleBinding): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(viewGroup : ViewGroup, i : Int) : ListViewHolder {
        val binding = ItemScheduleBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder : ListViewHolder, position : Int) {
        val schedule = listSchedule[position]
        holder.binding.apply {
            doSchedule.text = schedule.time
            actionSchedule.text = schedule.title
            actionDesc.text = schedule.description
        }
    }

    override fun getItemCount() : Int = listSchedule.size

}