package campus.tech.kakao.map

import android.content.ContentValues
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
        val selectList = findViewById<RecyclerView>(R.id.selectList)

        val db = MapDbHelper(context = this)
        val wDb = db.writableDatabase
        val rDb = db.readableDatabase

        for (i in 0..20) {
            val values = ContentValues()
            values.put(Map.TABLE_COLUMN_NAME, "카페" + i)
            values.put(Map.TABLE_COLUMN_ADDRESS, "서울 성동구 성수동" + i)
            values.put(Map.TABLE_COLUMN_CATEGORY, "카페")

            wDb.insert(Map.TABLE_NAME, null, values)
        }

        val cursor = rDb.rawQuery("Select * from ${Map.TABLE_NAME}", null)
        val mapItemList = mutableListOf<MapItem>()
        while (cursor.moveToNext()) {
            mapItemList.add(
                MapItem(
                    cursor.getString(cursor.getColumnIndexOrThrow(Map.TABLE_COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Map.TABLE_COLUMN_ADDRESS)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Map.TABLE_COLUMN_CATEGORY))
                )
            )
        }

        cursor.close()


        val selectItemList = mutableListOf<SelectItem>()
        for (i in 0..20) {
            selectItemList.add(SelectItem("약국" + i))
        }

        mapList.adapter = MapListAdapter(mapItemList, LayoutInflater.from(this))
        mapList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        selectList.adapter = SelectListAdapter(selectItemList, LayoutInflater.from(this))
        selectList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }
}


