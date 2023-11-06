import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class GenericAdapter<T>(
    private val layoutResId: Int,
    private val onBind: (View, T) -> Unit,
    private val onItemClickListener: OnItemClickListener<T>? = null
) : RecyclerView.Adapter<GenericAdapter<T>.ViewHolder>() {
    private var data: List<T> = emptyList()
    interface OnItemClickListener<T> {
        fun onItemClick(item: T)
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        onBind(holder.itemView, data[position])
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(newData: List<T>) {
        data = newData
        notifyDataSetChanged()
    }
}
