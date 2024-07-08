package campus.tech.kakao.map.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import campus.tech.kakao.map.R
import campus.tech.kakao.map.model.SavePlace

class SavePlaceAdapter(
    private val savePlaces: List<SavePlace>
) : RecyclerView.Adapter<SavePlaceAdapter.SavePlaceViewHolder>() {
    class SavePlaceViewHolder(
        itemView: View,
    ) : RecyclerView.ViewHolder(itemView) {
        private val savePlaceTextView: TextView = itemView.findViewById(R.id.savePlace)

        fun bind(savePlace: SavePlace) {
            savePlaceTextView.text = savePlace.savePlace
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SavePlaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.saveplace_item, parent, false)
        return SavePlaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: SavePlaceViewHolder, position: Int) {
        val savePlace = savePlaces[position]
        holder.bind(savePlace)
    }

    override fun getItemCount(): Int {
        return savePlaces.size
    }
}