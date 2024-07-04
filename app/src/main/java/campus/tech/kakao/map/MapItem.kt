package campus.tech.kakao.map

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView


data class MapItem(
    val name: String,
    val address: String,
    val category: String
)

object MapItemDB : BaseColumns {
    const val TABLE_NAME = "map"
    const val TABLE_COLUMN_NAME = "name"
    const val TABLE_COLUMN_ADDRESS = "address"
    const val TABLE_COLUMN_CATEGORY = "category"
}

class MapItemDbHelper(context: Context) : SQLiteOpenHelper(context, "map.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${MapItemDB.TABLE_NAME} (" +
                    "${MapItemDB.TABLE_COLUMN_NAME} varchar(15) not null," +
                    "${MapItemDB.TABLE_COLUMN_ADDRESS} varchar(30) not null," +
                    "${MapItemDB.TABLE_COLUMN_CATEGORY} varchar(10) not null" +
                    ");"
        )
        insertMapItem("카페", "서울 성동구 성수동","카페")
        insertMapItem("약국", "서울 강남구 대치동","약국")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${MapItemDB.TABLE_NAME}")
        onCreate(db)
    }

    private fun insertMapItem(name : String, address : String, category : String) {
        val wDb = writableDatabase

        for (i in 1..20) {
            val values = ContentValues()
            values.put(MapItemDB.TABLE_COLUMN_NAME, name + i)
            values.put(MapItemDB.TABLE_COLUMN_ADDRESS, address + i)
            values.put(MapItemDB.TABLE_COLUMN_CATEGORY, category)

            wDb.insert(MapItemDB.TABLE_NAME, null, values)
        }
    }

    fun makeMapItemList() : MutableList<MapItem> {
        val rDb = readableDatabase

        val cursor = rDb.rawQuery("Select * from ${MapItemDB.TABLE_NAME}", null)
        val mapItemList = mutableListOf<MapItem>()
        while (cursor.moveToNext()) {
            mapItemList.add(
                MapItem(
                    cursor.getString(cursor.getColumnIndexOrThrow(MapItemDB.TABLE_COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(MapItemDB.TABLE_COLUMN_ADDRESS)),
                    cursor.getString(cursor.getColumnIndexOrThrow(MapItemDB.TABLE_COLUMN_CATEGORY))
                )
            )
        }
        cursor.close()

        return mapItemList
    }
}