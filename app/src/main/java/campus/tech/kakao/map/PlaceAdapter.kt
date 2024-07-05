package campus.tech.kakao.map

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlaceAdapter(private val items : List<Place>) : RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        val id : TextView = view.findViewById(R.id.id)
        val name: TextView = view.findViewById(R.id.name)
        val address: TextView = view.findViewById(R.id.address)
        val kind: TextView = view.findViewById(R.id.kind)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent?.context).inflate(R.layout.item_recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        with(holder) {
            id.text = item.id.toString()
            name.text = item.name
            address.text = item.address
            kind.text = item.kind
            Log.d("Testt",holder.name.text.toString())
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}