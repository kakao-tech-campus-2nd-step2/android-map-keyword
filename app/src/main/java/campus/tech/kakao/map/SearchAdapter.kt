package campus.tech.kakao.map

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import campus.tech.kakao.map.databinding.ItemResultBinding

class SearchAdapter(private val onItemClicked: (String) -> Unit) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private val results = mutableListOf<String>()

    class ViewHolder(val binding: ItemResultBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder.binding.resultTextView.text = results[position]
        val result = results[position].split(", ")
        val name = result[0].split(": ")[1]
        val address = result[1].split(": ")[1]
        val category = result[2].split(": ")[1]

        holder.binding.resultTextView.text = name
        holder.binding.resultAddressTextView.text = address
        holder.binding.resultCategoryTextView.text = category

        holder.binding.root.setOnClickListener {
            onItemClicked(results[position])
        }
    }

    override fun getItemCount() = results.size

    fun updateResults(newResults: List<String>) {
        results.clear()
        results.addAll(newResults)
        notifyDataSetChanged()
    }
}