package campus.tech.kakao.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapList = findViewById<RecyclerView>(R.id.mapList)
        val mapItemList = mutableListOf<MapItem>()
        for (i in 0..20) {
            mapItemList.add(MapItem("카페" + i, "복현동" + i, "카페"))
        }

        mapList.adapter = MapListAdapter(mapItemList, LayoutInflater.from(this))
        mapList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}

class MapListAdapter(
    val mapItemList: MutableList<MapItem>, val layoutInflater: LayoutInflater
) : RecyclerView.Adapter<MapListAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView
        val address: TextView
        val category: TextView

        init {
            name = itemView.findViewById<TextView>(R.id.name)
            address = itemView.findViewById<TextView>(R.id.address)
            category = itemView.findViewById<TextView>(R.id.category)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = layoutInflater.inflate(R.layout.map_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = mapItemList.get(position).name
        holder.address.text = mapItemList.get(position).address
        holder.category.text = mapItemList.get(position).category
    }

    override fun getItemCount(): Int {
        return mapItemList.size
    }
}


