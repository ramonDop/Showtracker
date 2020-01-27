package com.example.showtracker.history

import android.content.Context
import android.view.LayoutInflater.*
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.showtracker.model.Show
import kotlinx.android.synthetic.main.item_show.view.*
import android.view.View
import com.example.showtracker.R

class HistoryAdapter(private val shows: List<Show>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {


    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            from(context).inflate(R.layout.item_show, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return shows.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(shows[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @Suppress("DEPRECATION")
        fun bind(show: Show) {
            itemView.tvTitle.text = show.title
            itemView.tvEpisode.text = show.rating
        }
    }


}