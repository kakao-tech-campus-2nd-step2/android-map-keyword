package campus.tech.kakao.map

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SavedSearchAdapter(private val items : List<SavedSearch> , private val onCloseClick : (SavedSearch) -> Unit) : RecyclerView.Adapter<SavedSearchAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        val close : ImageView = view.findViewById(R.id.close)
        val id : TextView = view.findViewById(R.id.saved_search_id)
        val name: TextView = view.findViewById(R.id.saved_search_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedSearchAdapter.ViewHolder {
        val view: View =
            LayoutInflater.from(parent?.context).inflate(R.layout.item_saved_search, parent, false)
        return SavedSearchAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SavedSearchAdapter.ViewHolder, position: Int) {
        val item = items[position]
        with(holder) {
            id.text = item.id.toString()
            name.text = item.name
            close.setOnClickListener {
                // 클릭된 아이템의 이름으로 SavedSearch에서 제거
//                db.deleteSavedPlace(item.name)
                // 리스트 업데이트
//                updateSavedSearch(dbManager, recyclerView)
                onCloseClick(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}

