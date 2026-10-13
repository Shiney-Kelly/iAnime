package com.project.ianime.feature.gallery.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.ianime.R
import com.project.ianime.core.data.remote.dto.AnimeApiModel

class AnimeItemAdapter(
    private val clickHandler: (String) -> Unit
) : RecyclerView.Adapter<AnimeItemViewHolder>() {

    private var animeList = mutableListOf<AnimeApiModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun setAnimeList(animeData: List<AnimeApiModel>) {
        this.animeList = animeData.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_anime, parent, false)
        return AnimeItemViewHolder((itemView))
    }

    override fun getItemCount(): Int {
        return animeList.size
    }

    override fun onBindViewHolder(holder: AnimeItemViewHolder, position: Int) {
        val targetAnime = animeList[position]
        holder.bind(targetAnime)
        // Set click item event
        holder.itemView.setOnClickListener {
            clickHandler(targetAnime.animeId)
        }
    }
}