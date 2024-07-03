package campus.tech.kakao.map.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import campus.tech.kakao.map.R
import campus.tech.kakao.map.models.SearchResult

class SearchResultAdapter(
    private val inflater: LayoutInflater,
    val onItemClicked: ((item: SearchResult, index: Int) -> Unit)
) :
    RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>() {
    class SearchResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val resultName: TextView
        val resultAddress: TextView
        val resultType: TextView

        init {
            resultName = itemView.findViewById(R.id.text_result_name)
            resultAddress = itemView.findViewById(R.id.text_result_address)
            resultType = itemView.findViewById(R.id.text_result_type)
        }
    }

    var results: List<SearchResult> = listOf()

    fun updateResult(results: List<SearchResult>) {
        this.results = results
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val view = inflater.inflate(R.layout.item_search_result, parent, false)
        val holder = SearchResultViewHolder(view)
        view.setOnClickListener {
            onItemClicked(
                SearchResult(
                    holder.resultName.text.toString(),
                    holder.resultAddress.text.toString(),
                    holder.resultType.text.toString()
                ),
                holder.bindingAdapterPosition
            )
        }
        return holder
    }

    override fun getItemCount(): Int {
        return results.size
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val item = results[position]
        holder.resultAddress.text = item.address
        holder.resultName.text = item.name
        holder.resultType.text = item.type
    }
}