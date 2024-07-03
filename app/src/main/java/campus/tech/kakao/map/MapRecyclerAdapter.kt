package campus.tech.kakao.map

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class MapRecyclerAdapter(
    val locationList: List<Location>,
    val layoutInflater: LayoutInflater,
    val mContext: Context
) : RecyclerView.Adapter<MapRecyclerAdapter.MapViewHolder>() {
    inner class MapViewHolder(itemView: View) : ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.location_name)
        val category: TextView = itemView.findViewById(R.id.location_category)
        val address: TextView = itemView.findViewById(R.id.location_address)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapViewHolder {
        val view = layoutInflater.inflate(R.layout.item_search_result, parent, false)
        return MapViewHolder(view)
    }

    override fun getItemCount(): Int {
        return locationList.size
    }

    override fun onBindViewHolder(holder: MapViewHolder, position: Int) {
        holder.name.text = locationList[position].name
        holder.category.text = locationList[position].category
        holder.address.text = locationList[position].address
    }
}