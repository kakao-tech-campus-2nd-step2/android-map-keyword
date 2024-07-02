package campus.tech.kakao.map

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import campus.tech.kakao.map.LocationContract.LocationEntry

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbHelper = LocationDbHelper(context=this)
        addLocationData(dbHelper)
        readLocationData(dbHelper)
    }

    private fun readLocationData(dbHelper: LocationDbHelper) {
        val db = dbHelper.readableDatabase
        val projection = arrayOf(
            LocationEntry.COLUMN_NAME_TITLE,
            LocationEntry.COLUMN_NAME_ADDRESS,
            LocationEntry.COLUMN_NAME_CATEGORY
        )

        val selection = "${LocationEntry.COLUMN_NAME_CATEGORY} = ?"
        val selectionArgs = arrayOf("카페")

        val sortOrder = "${LocationEntry.COLUMN_NAME_TITLE} ASC"

        val cursor = db.query(
            LocationEntry.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )
        val result = mutableListOf<String>()
        while (cursor.moveToNext()) {
            result.add(
                cursor.getString(
                    cursor.getColumnIndexOrThrow(LocationEntry.COLUMN_NAME_TITLE)
                )
            )
        }
        Log.d("jieun", "$result")
        cursor.close()
    }

    private fun addLocationData(dbHelper: LocationDbHelper) {
        val db = dbHelper.writableDatabase

        for(i in 1..9) {
            val values = ContentValues()
            values.put(LocationEntry.COLUMN_NAME_TITLE, "카페"+i)
            values.put(LocationEntry.COLUMN_NAME_ADDRESS, "부산 부산진구 전포대로"+i)
            values.put(LocationEntry.COLUMN_NAME_CATEGORY, "카페")

            db?.insert(LocationEntry.TABLE_NAME, null, values)
        }
        for(i in 1..9) {
            val values = ContentValues()
            values.put(LocationEntry.COLUMN_NAME_TITLE, "음식점"+i)
            values.put(LocationEntry.COLUMN_NAME_ADDRESS, "부산 부산진구 중앙대로"+i)
            values.put(LocationEntry.COLUMN_NAME_CATEGORY, "음식점")

            db?.insert(LocationEntry.TABLE_NAME, null, values)
        }
    }
}
