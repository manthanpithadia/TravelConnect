import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.travelconnect.R
import com.google.android.material.chip.Chip

class ChipAdapter(private val items: List<String>) :
    RecyclerView.Adapter<ChipAdapter.ChipViewHolder>() {

    inner class ChipViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChipViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.chip_item, parent, false)
        return ChipViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChipViewHolder, position: Int) {
        val chip = holder.itemView as Chip
        val item = items[position]
        chip.text = item
    }

    override fun getItemCount() = items.size
}
