package campus.tech.kakao.map.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import campus.tech.kakao.map.model.Location
import campus.tech.kakao.map.R

class LocationListviewAdapter(
    private val items: List<Location>
): BaseAdapter() {
    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Location = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View =
            LayoutInflater
                .from(parent?.context)
                .inflate(R.layout.list_item, parent, false)

        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvLocation: TextView = view.findViewById(R.id.tvLocation)
        val tvType: TextView = view.findViewById(R.id.tvType)

        val item = getItem(position)

        tvName.text = item.name
        tvLocation.text = item.location
        tvType.text = item.type

        return view
    }
}