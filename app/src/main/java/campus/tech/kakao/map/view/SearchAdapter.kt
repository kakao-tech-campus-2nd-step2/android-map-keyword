package campus.tech.kakao.map.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import campus.tech.kakao.map.R
import campus.tech.kakao.map.model.Place

class SearchAdapter(private val places: List<Place>) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.placeName)
        val addressTextView: TextView = itemView.findViewById(R.id.placeAddress)
        val categoryTextView: TextView = itemView.findViewById(R.id.placeCategory)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchAdapter.SearchViewHolder, position: Int) {
        val place = places[position]
        holder.nameTextView.text = place.name
        holder.addressTextView.text = place.address
        holder.categoryTextView.text = place.category
    }

    override fun getItemCount(): Int {
        return places.size
    }


}