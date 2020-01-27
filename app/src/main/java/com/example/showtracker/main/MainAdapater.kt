package com.example.showtracker.main

import android.content.Context
import android.view.LayoutInflater.*
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.showtracker.model.Show
import kotlinx.android.synthetic.main.item_show.view.*
import android.view.View
import com.example.showtracker.R


class MainAdapater(private val shows: List<Show>, private val onClick: (Show) -> Unit) :
    RecyclerView.Adapter<MainAdapater.ViewHolder>() {

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

        init {
            itemView.setOnClickListener { onClick(shows[adapterPosition]) }
        }

        @Suppress("DEPRECATION")
        fun bind(show: Show) {
            itemView.tvTitle.text = show.title
            itemView.tvEpisode.text = show.rating
            itemView.ivEdit.setImageDrawable(context.getDrawable(R.drawable.ic_edit_black_24dp))
        }
    }


}