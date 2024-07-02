package campus.tech.kakao.map

import android.content.ContentValues
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

object MapDB : BaseColumns {
    const val TABLE_NAME = "map"
    const val TABLE_COLUMN_NAME = "name"
    const val TABLE_COLUMN_ADDRESS = "address"
    const val TABLE_COLUMN_CATEGORY = "category"
}

class MapDbHelper(context: Context) : SQLiteOpenHelper(context, "map.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${MapDB.TABLE_NAME} (" +
                    "${MapDB.TABLE_COLUMN_NAME} varchar(15) not null," +
                    "${MapDB.TABLE_COLUMN_ADDRESS} varchar(30) not null," +
                    "${MapDB.TABLE_COLUMN_CATEGORY} varchar(10) not null" +
                    ");"
        )
        insertMapItem("카페", "서울 성동구 성수동","카페")
        insertMapItem("약국", "서울 강남구 대치동","약국")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${MapDB.TABLE_NAME}")
        onCreate(db)
    }

    fun insertMapItem(name : String, address : String, category : String) {
        val wDb = writableDatabase

        for (i in 1..20) {
            val values = ContentValues()
            values.put(MapDB.TABLE_COLUMN_NAME, name + i)
            values.put(MapDB.TABLE_COLUMN_ADDRESS, address + i)
            values.put(MapDB.TABLE_COLUMN_CATEGORY, category)

            wDb.insert(MapDB.TABLE_NAME, null, values)
        }
    }

    fun makeMapList() : MutableList<MapItem>{
        val rDb = readableDatabase

        val cursor = rDb.rawQuery("Select * from ${MapDB.TABLE_NAME}", null)
        val mapItemList = mutableListOf<MapItem>()
        while (cursor.moveToNext()) {
            mapItemList.add(
                MapItem(
                    cursor.getString(cursor.getColumnIndexOrThrow(MapDB.TABLE_COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(MapDB.TABLE_COLUMN_ADDRESS)),
                    cursor.getString(cursor.getColumnIndexOrThrow(MapDB.TABLE_COLUMN_CATEGORY))
                )
            )
        }
        cursor.close()

        return mapItemList
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