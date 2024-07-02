package campus.tech.kakao.map

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.EditText
import campus.tech.kakao.map.R

class MainActivity : AppCompatActivity() {
    private lateinit var searchEditText: EditText
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchEditText = findViewById(R.id.edit_search)
        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val dbHelper = DatabaseHelper(this)
        val db = dbHelper.writableDatabase

        for (i in 1..10) {
            val insertData =
                "INSERT INTO ${DatabaseHelper.TABLE_NAME} (${DatabaseHelper.COLUMN_NAME}, ${DatabaseHelper.COLUMN_ADDRESS}) VALUES ('cafe$i', '대전 유성구 봉명동 $i')"
            Log.d("database", "찍힘")
            val insertData2 =
                "INSERT INTO ${DatabaseHelper.TABLE_NAME} (${DatabaseHelper.COLUMN_NAME}, ${DatabaseHelper.COLUMN_ADDRESS}) VALUES ('Pharmacy$i', '대전 유성구 봉명동 $i')"
            Log.d("database", "찍힘")
            val insertData3 =
                "INSERT INTO ${DatabaseHelper.TABLE_NAME} (${DatabaseHelper.COLUMN_NAME}, ${DatabaseHelper.COLUMN_ADDRESS}) VALUES ('Cinema$i', '대전 유성구 봉명동 $i')"
            Log.d("database", "찍힘")

            db.execSQL(insertData)
            db.execSQL(insertData2)
            db.execSQL(insertData3)
        }
    }

    override fun onDestroy() {
        val dbHelper = DatabaseHelper(this)
        dbHelper.close()
        super.onDestroy()
    }
}
