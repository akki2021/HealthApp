package com.example.healthapp.ui.videos.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.healthapp.R
import com.example.healthapp.ui.videos.data.VideoModel

class VideoAdapter(private val videos: List<VideoModel>) : RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_videos, parent, false)

        return ViewHolder(view)
    }

    private var onClick: OnItemClicked? = null

    interface OnItemClicked {
        fun onItemClick(position: Int)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val videoLecturesModel = videos[position]

        holder.videoTitle.text = videoLecturesModel.videoTitle
        Glide.with(holder.videoThumbnail.context)
            .load(videoLecturesModel.videoThumbnailUrl)
            .centerCrop()
            .into(holder.videoThumbnail)

        holder.videoThumbnail.setOnClickListener { onClick!!.onItemClick(position) }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return videos.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val videoTitle: TextView = itemView.findViewById(R.id.videoTitle)
        val videoThumbnail: ImageView = itemView.findViewById(R.id.videoThumbnail)
    }

    fun setOnClick(onClick: OnItemClicked?) {
        this.onClick = onClick
    }
}

