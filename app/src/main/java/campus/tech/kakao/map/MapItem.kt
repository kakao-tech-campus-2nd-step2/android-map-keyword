package campus.tech.kakao.map

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class MapItem(
    val name: String,
    val address: String,
    val category: String
)

object Map : BaseColumns {
    const val TABLE_NAME = "map"
    const val TABLE_COLUMN_NAME = "name"
    const val TABLE_COLUMN_ADDRESS = "address"
    const val TABLE_COLUMN_CATEGORY = "category"
}

class MapDbHelper(context: Context) : SQLiteOpenHelper(context, "map.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${Map.TABLE_NAME} (" +
                    "${Map.TABLE_COLUMN_NAME} varchar(15) not null," +
                    "${Map.TABLE_COLUMN_ADDRESS} varchar(30) not null," +
                    "${Map.TABLE_COLUMN_CATEGORY} varchar(10) not null" +
                    ");"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${Map.TABLE_NAME}")
        onCreate(db)
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