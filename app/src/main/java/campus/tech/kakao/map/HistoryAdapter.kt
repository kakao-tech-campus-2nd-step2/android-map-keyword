package campus.tech.kakao.map

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class HistoryAdapter(private val items: List<String>): BaseAdapter() {
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
                LayoutInflater.from(parent?.context).inflate(R.layout.search_history_item, parent, false)
            placeViewHolder = PlaceViewHolder(view)
            view.tag = placeViewHolder
        } else {
            view = convertView
            placeViewHolder = convertView.tag as PlaceViewHolder
        }

        val item = items[position]
        placeViewHolder.history.text = item
        return view
    }

    class PlaceViewHolder(view: View) {
        val history: TextView = view.findViewById(R.id.history)
    }
}