package campus.tech.kakao.map.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import campus.tech.kakao.map.view.OnClickSavedPlaceListener
import campus.tech.kakao.map.R
import campus.tech.kakao.map.model.SavedPlace

class SavedPlaceViewAdapter(
    val savedPlaceList: LiveData<MutableList<SavedPlace>>,
    val inflater: LayoutInflater,
    val listener: OnClickSavedPlaceListener
) : RecyclerView.Adapter<SavedPlaceViewAdapter.SavedPlaceViewHolder>() {

    inner class SavedPlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.saved_place_name)
        val deleteButton = itemView.findViewById<ImageView>(R.id.button_saved_delete)

        init {
            deleteButton.setOnClickListener {
                val position = absoluteAdapterPosition
                Log.d("testt", "삭제 콜백함수 호출")
                savedPlaceList.value?.get(position)?.let { listener.deleteSavedPlace(it, position) }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedPlaceViewHolder {
        val view = inflater.inflate(R.layout.saved_place_item, parent, false)
        Log.d("testt", "저장된 장소를 띄우는 뷰 홀더 생성")
        return SavedPlaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: SavedPlaceViewHolder, position: Int) {
        holder.name.text = savedPlaceList.value?.get(position)?.name ?: ""
    }

    override fun getItemCount(): Int {
        return savedPlaceList.value?.size ?: 0
    }
}
