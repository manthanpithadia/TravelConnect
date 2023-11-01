package com.example.travelconnect.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.travelconnect.R
import com.example.travelconnect.data.model.CardItem

class CardTypeOneAdapter(private val context: Context, private val cardItems: List<CardItem>) :
    RecyclerView.Adapter<CardTypeOneAdapter.CardViewHolder>() {

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.img_background)
        val titleTextView: TextView = itemView.findViewById(R.id.txt_title)
        val locationTextView: TextView = itemView.findViewById(R.id.txt_location)
        val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
        val ratingCountTextView: TextView = itemView.findViewById(R.id.txt_rating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cardview_type1, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val item = cardItems[position]

        holder.imageView.setImageResource(item.imageResId)
        holder.titleTextView.text = item.title
        holder.locationTextView.text = item.location
        holder.ratingBar.rating = item.rating
        holder.ratingCountTextView.text = item.ratingCount
    }

    override fun getItemCount(): Int {
        return cardItems.size
    }
}
