package campus.tech.kakao.map.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import campus.tech.kakao.map.view.OnClickPlaceListener
import campus.tech.kakao.map.R
import campus.tech.kakao.map.model.Place

class PlaceViewAdapter(
    val placeList: LiveData<MutableList<Place>>,
    val inflater: LayoutInflater,
    val listener: OnClickPlaceListener
) : RecyclerView.Adapter<PlaceViewAdapter.PlaceViewHolder>() {

    inner class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.place_name)
        val location = itemView.findViewById<TextView>(R.id.place_location)
        val category = itemView.findViewById<TextView>(R.id.place_category)

        init {
            itemView.setOnClickListener {
                val position = absoluteAdapterPosition
                Log.d("testt", "콜백함수 호출")
                placeList.value?.get(position)?.let { listener.savePlace(it) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val view = inflater.inflate(R.layout.place_item, parent, false)
        Log.d("testt", "검색 결과 뷰 생성")
        return PlaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.name.text = placeList.value?.get(position)?.name ?: ""
        holder.location.text = placeList.value?.get(position)?.location ?: ""
        holder.category.text = placeList.value?.get(position)?.category ?: ""
    }

    override fun getItemCount(): Int {
        return placeList.value?.size ?: 0
    }
}