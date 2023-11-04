import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.example.travelconnect.R
import com.squareup.picasso.Picasso

class GridImageAdapter(private val context: Context) : BaseAdapter() {
    private var imageList: List<ImageModel>? = null

    fun setData(imageList: List<ImageModel>?) {
        this.imageList = imageList
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return imageList?.size ?: 0
    }

    override fun getItem(position: Int): Any {
        return imageList!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val imageModel = getItem(position) as ImageModel
        val itemView: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            // If the view is not recycled, inflate the layout
            val inflater = LayoutInflater.from(context)
            itemView = inflater.inflate(R.layout.grid_item, parent, false)

            // Create and set the ViewHolder
            viewHolder = ViewHolder()
            viewHolder.imageView = itemView.findViewById(R.id.gridImageView)
            itemView.tag = viewHolder
        } else {
            // Recycle the view
            itemView = convertView
            viewHolder = itemView.tag as ViewHolder
        }

        // Use Picasso to load the image into the ImageView
        Picasso.get()
            .load(imageModel.img)
            .resize(200, 200)
            .centerCrop()
            .into(viewHolder.imageView)

        return itemView
    }

    private class ViewHolder {
        lateinit var imageView: ImageView
    }
}
