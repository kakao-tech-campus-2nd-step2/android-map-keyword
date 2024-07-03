package campus.tech.kakao.map.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import campus.tech.kakao.map.R

class SearchKeywordAdapter(
    private val inflater: LayoutInflater,
    val onItemClicked: ((item: String) -> Unit)
) : RecyclerView.Adapter<SearchKeywordAdapter.SearchKeywordViewHolder>() {
    private var items: List<String> = listOf()

    class SearchKeywordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameText: TextView

        init {
            nameText = itemView.findViewById(R.id.text_search_keyword)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchKeywordViewHolder {
        val view = inflater.inflate(R.layout.item_search_keyword, parent, false)
        val holder = SearchKeywordViewHolder(view)
        view.setOnClickListener {
            onItemClicked(holder.nameText.text.toString())
        }
        return holder
    }

    override fun onBindViewHolder(holder: SearchKeywordViewHolder, position: Int) {
        val item = items[position]
        holder.nameText.text = item
    }

    override fun getItemId(position: Int): Long = position.toLong()
    override fun getItemCount(): Int = items.size

    fun updateKeywords(keywords: List<String>) {
        items = keywords
        notifyDataSetChanged()
    }
}