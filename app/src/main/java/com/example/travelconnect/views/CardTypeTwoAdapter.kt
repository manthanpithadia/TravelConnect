import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.example.travelconnect.R

class CardTypeTwoAdapter(private val context: Context, private val activityItems: List<ActivityItem>) :
    RecyclerView.Adapter<CardTypeTwoAdapter.CardViewHolder>() {

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.img_card_background)
        val titleTextView: TextView = itemView.findViewById(R.id.txt_card_title)
        val locationTextView: TextView = itemView.findViewById(R.id.txt_card_location)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cardview_type2, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val item = activityItems[position]

        // Load the image using Picasso (make sure to add Picasso dependency)
        Picasso.get().load(item.img).resize(250, 250)
            .centerCrop().into(holder.imageView)
        holder.titleTextView.text = item.name
        holder.locationTextView.text = item.province
    }

    override fun getItemCount(): Int {
        return activityItems.size
    }
}
