package campus.tech.kakao.map

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources

class PlaceAdapter(private val items: List<Place>): BaseAdapter() {
    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val placeViewHolder : PlaceViewHolder
        if (convertView == null) {
            view =
                LayoutInflater.from(parent?.context).inflate(R.layout.place_item, parent, false)
            placeViewHolder = PlaceViewHolder(view)
            view.tag = placeViewHolder
        } else {
            view = convertView
            placeViewHolder = convertView.tag as PlaceViewHolder
        }

        val item = items[position]
        placeViewHolder.place.text = item.name
        placeViewHolder.address.text = item.address
        placeViewHolder.category.text = item.category
        return view
    }

    class PlaceViewHolder(view: View) {
        val place: TextView = view.findViewById(R.id.place)
        val address: TextView = view.findViewById(R.id.address)
        val category: TextView = view.findViewById(R.id.category)
    }
}