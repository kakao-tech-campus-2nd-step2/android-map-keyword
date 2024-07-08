package campus.tech.kakao.map

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LocationAdapter(
    val locationList: List<LocationData>,
    private val onItemClick: (LocationData) -> Unit // 콜백 함수
    ): RecyclerView.Adapter<LocationAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name_text_view: TextView
        val location_text_view: TextView
        val category_text_view: TextView

        init {
            name_text_view = itemView.findViewById(R.id.name)
            location_text_view = itemView.findViewById(R.id.location)
            category_text_view = itemView.findViewById(R.id.category)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = locationList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val locations = locationList[position]
        holder.name_text_view.text = locations.name
        holder.location_text_view.text = locations.location
        holder.category_text_view.text = locations.category

        holder.itemView.setOnClickListener {
            onItemClick(locations)
        }
    }
}