package com.example.travelconnect.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.travelconnect.R
import com.example.travelconnect.data.model.CardItemTypeTwo

class CardTypeTwoAdapter(private val context: Context, private val cardItems: List<CardItemTypeTwo>) :
    RecyclerView.Adapter<CardTypeTwoAdapter.HorizontalCardViewHolder>() {

    inner class HorizontalCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val backgroundImageView: ImageView = itemView.findViewById(R.id.img_card_background)
        val titleTextView: TextView = itemView.findViewById(R.id.txt_card_title)
        val locationTextView: TextView = itemView.findViewById(R.id.txt_card_location)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalCardViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cardview_type2, parent, false)
        return HorizontalCardViewHolder(view)
    }

    override fun onBindViewHolder(holder: HorizontalCardViewHolder, position: Int) {
        val item = cardItems[position]

        holder.backgroundImageView.setImageResource(item.imageResId)
        holder.titleTextView.text = item.title
        holder.locationTextView.text = item.location
    }

    override fun getItemCount(): Int {
        return cardItems.size
    }
}
