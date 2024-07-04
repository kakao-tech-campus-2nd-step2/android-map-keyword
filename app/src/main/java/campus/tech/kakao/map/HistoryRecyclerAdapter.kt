package campus.tech.kakao.map

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class HistoryRecyclerAdapter(
    val history: List<String>,
    val layoutInflater: LayoutInflater
) : RecyclerView.Adapter<HistoryRecyclerAdapter.HistoryViewHolder>() {
    inner class HistoryViewHolder(itemView: View) : ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.history_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = layoutInflater.inflate(R.layout.item_search_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.name.text = history[position]
    }

    override fun getItemCount(): Int {
        return history.size
    }
}