import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.travelconnect.R
import com.squareup.picasso.Picasso

class CardTypeOneAdapter(private val context: Context, private val locationItems: List<LocationItem>) :
    RecyclerView.Adapter<CardTypeOneAdapter.CardViewHolder>() {

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.img_background)
        val titleTextView: TextView = itemView.findViewById(R.id.txt_title)
        val locationTextView: TextView = itemView.findViewById(R.id.txt_location)
        val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cardview_type1, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val item = locationItems[position]

        // Load the image using Picasso (you may need to add Picasso as a dependency)
        Picasso.get().load(item.img).resize(250, 250)
            .centerCrop().into(holder.imageView)

        holder.titleTextView.text = item.name
        holder.locationTextView.text = item.province
        holder.ratingBar.rating = item.rating.toFloat()
    }

    override fun getItemCount(): Int {
        return locationItems.size
    }
}
