package campus.tech.kakao.map

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SearchAdapter() : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    var searchDataList: List<SearchData> = emptyList()
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val searchName: TextView = view.findViewById(R.id.searchName)
        private val searchAddress: TextView = view.findViewById(R.id.searchAddress)
        private val searchCategory: TextView = view.findViewById(R.id.searchCategory)

        fun bind(searchData: SearchData) {
            searchName.text = searchData.name
            searchAddress.text = searchData.address
            searchCategory.text = searchData.category
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return searchDataList.size
    }

    override fun onBindViewHolder(holder: SearchAdapter.ViewHolder, position: Int) {
        holder.bind(searchDataList[position])
    }
}