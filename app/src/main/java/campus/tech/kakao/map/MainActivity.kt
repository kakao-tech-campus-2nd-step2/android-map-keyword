package campus.tech.kakao.map

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var db: DataDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
 
        createQuery()
    }

    private fun createQuery() {
        db = DataDbHelper(context = this)

        try {
            Log.d("MainActivity", "Opening database")
            val writeDB = db.writableDatabase

            writeDB.execSQL("DELETE FROM LOCATION") //시작하기 전 이 쿼리문을 추가한다.

            Log.d("MainActivity", "Inserting data")
            for (i in 1..10) {
                val values = ContentValues().apply {
                    put("name", "카페$i")
                    put("location", "광주 북구 용봉동$i")
                    put("category", "카페")
                }
                writeDB.insert("LOCATION", null, values)
            }
            for (i in 1..10) {
                val values = ContentValues().apply {
                    put("name", "약국$i")
                    put("location", "전주 완산구 효자동$i")
                    put("category", "약국")
                }
                writeDB.insert("LOCATION", null, values)
            }


            Log.d("MainActivity", "Reading data")
            val readDB = db.readableDatabase

            val cursor = readDB.query(
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
            Log.d("MainActivity", "Database operations completed")
        } catch (e: Exception) {
            Log.e("MainActivity", "Database error: ${e.message}", e)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        db.close()
        Log.d("MainActivity", "Database closed")
    }
}