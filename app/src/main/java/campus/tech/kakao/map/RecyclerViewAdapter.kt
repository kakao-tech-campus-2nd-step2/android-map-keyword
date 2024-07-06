package campus.tech.kakao.map

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(
	val placeList: MutableList<Place>
): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
	inner class ViewHolder(
		itemView: View
	): RecyclerView.ViewHolder(itemView) {
		val name:TextView = itemView.findViewById(R.id.name)
		val address:TextView = itemView.findViewById(R.id.address)
		val type:TextView = itemView.findViewById(R.id.type)
		init {
			itemView.setOnClickListener {
				val position:Int = bindingAdapterPosition
				val place:Place = placeList[position]
				Log.d("testt", "Clicked on ${place.name}")
			}
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false))
	}

	override fun getItemCount(): Int {
		return placeList.size
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.name.text = placeList[position].name
		holder.address.text = placeList[position].address
		holder.type.text = placeList[position].type
	}
}


