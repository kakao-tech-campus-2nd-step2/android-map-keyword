package campus.tech.kakao.map

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = DataDbHelper(context = this)
        db.openDatabases()
        val writeDB = db.writableDatabase

        for (i in 1..10) {
            val values = ContentValues().apply {
                put("name", "카페$i")
                put("location", "광주 북구 용봉동$i")
                put("category", "카페")
            }
            writeDB.insert("LOCATION", null, values)

        }
        val rDb = db.readableDatabase

        val cursor = rDb.query(
            "LOCATION",
            null,
            null,
            null,
            null,
            null,
            null
        )

        cursor.use { cur ->
            while (cur.moveToNext()) {
                val name = cur.getString(cur.getColumnIndexOrThrow("name"))
                val location = cur.getString(cur.getColumnIndexOrThrow("location"))
                val category = cur.getString(cur.getColumnIndexOrThrow("category"))
                Log.d("MainActivity", "Name: $name, Location: $location, Category: $category")
            }
        }

    }
}
