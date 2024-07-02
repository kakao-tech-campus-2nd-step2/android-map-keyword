package campus.tech.kakao.map

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SelectItem(
    val name: String
)

class SelectListAdapter(
    val selectItemList: MutableList<SelectItem>, val layoutInflater: LayoutInflater
) : RecyclerView.Adapter<SelectListAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView

        init {
            name = itemView.findViewById<TextView>(R.id.name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = layoutInflater.inflate(R.layout.select_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = selectItemList.get(position).name
    }

    override fun getItemCount(): Int {
        return selectItemList.size
    }
}