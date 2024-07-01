package campus.tech.kakao.map

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.listLocation)

        val db = DBHelper(this)
        val wDb = db.writableDatabase

        val values = ContentValues()

        values.put(LocationContract.COLUMN_NAME, "컴포즈커피")
        values.put(LocationContract.COLUMN_LOCATION, "부산시 금정구")
        values.put(LocationContract.COLUMN_TYPE, "카페")

        wDb.insert(LocationContract.TABLE_NAME, null, values)

        val rDb = db.readableDatabase

        val cursor = rDb.query(
            LocationContract.TABLE_NAME,
            arrayOf(LocationContract.COLUMN_NAME,
                LocationContract.COLUMN_LOCATION,
                LocationContract.COLUMN_TYPE),
            null,
            null,
            null,
            null,
            null
        )

        val result = mutableListOf<String>()

        while (cursor.moveToNext()){
            result.add(
                cursor.getString(
                    cursor.getColumnIndexOrThrow(LocationContract.COLUMN_NAME)
                )
            )
        }

        Log.d("sqlite", "$result")

        cursor.close()

        /*val dummy: List<Location> = listOf(
            Location("카페 a", "부산시 해운대구", Type.카페 ),
            Location("카페 b", "부산시 해운대구", Type.카페 ),
            Location("카페 c", "부산시 해운대구", Type.카페 )
        )
        listView.adapter = LocationListviewAdapter(dummy)*/
    }
}
