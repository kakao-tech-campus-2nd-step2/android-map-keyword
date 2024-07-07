package campus.tech.kakao.map

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView

class RecentSearchAdapter(
    private val recentDataList: List<String>,
    private val viewModel: RecentViewModel
) : BaseAdapter() {

    class RecentViewHolder(view:View){
        val name : TextView = view.findViewById(R.id.recent_search)
        val deleteBtn : ImageButton = view.findViewById(R.id.delete_recent)
    }
    override fun getCount(): Int = recentDataList.size

    override fun getItem(position: Int): Any = recentDataList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(parent?.context)
            .inflate(R.layout.recent_search_item, parent, false)
        val recentDataViewHolder =
            view.tag as? RecentViewHolder ?: RecentViewHolder(view).also { view.tag = it }
        recentDataViewHolder.name.text = getItem(position) as String

        recentDataViewHolder.deleteBtn.setOnClickListener {
            viewModel.deleteRecentData(recentDataList[position])
        }

        return view
    }
}