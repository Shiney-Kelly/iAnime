package com.project.ianime.feature.gallery.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.ianime.R
import com.project.ianime.core.model.GalleryItem

class AnimeItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val animeImage: ImageView = view.findViewById(R.id.item_image)
    private val animeRate: TextView = view.findViewById(R.id.rate)
    private val animeTitle: TextView = view.findViewById(R.id.item_title)
    private val animeDescription: TextView = view.findViewById(R.id.item_description)

    fun bind(model: GalleryItem) {
        animeImage.setImageResource(R.drawable.ic_anime_placeholder)
        animeRate.text = model.rate
        animeTitle.text = model.displayName(itemView.context)
        animeDescription.text = model.displaySubtitle(itemView.context)
    }
}