package com.skinconnect.userapps.ui.helper

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter

class NoFilterArrayAdapter(context: Context, layout: Int, var values: Array<String>) : ArrayAdapter<String>(context, layout, values) {
    private val filterThatDoesNothing = object: Filter() {
        override fun performFiltering(p0: CharSequence?): FilterResults {
            val results = FilterResults()
            results.values = values
            results.count = values.size
            return results
        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults?) = notifyDataSetChanged()
    }

    override fun getFilter() = filterThatDoesNothing
}