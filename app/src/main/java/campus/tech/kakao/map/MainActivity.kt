package campus.tech.kakao.map

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = PlaceDbHelper(this)
        val writableDatabase = db.writableDatabase
        val values = ContentValues()
        values.put(PlaceContract.COLUMN_NAME_NAME, "카페1")
        values.put(PlaceContract.COLUMN_NAME_ADDRESS, "서울시 강남구")

        writableDatabase.insert(PlaceContract.TABLE_NAME, null, values)

        val readableDatabase = db.readableDatabase
        var searchResult = "%카페%"
        val cursor = readableDatabase.query(
            PlaceContract.TABLE_NAME,
            arrayOf(PlaceContract.COLUMN_NAME_NAME),
            "${PlaceContract.COLUMN_NAME_NAME} like ?",
            arrayOf(searchResult),
            null,
            null,
            "${PlaceContract.COLUMN_NAME_NAME} DESC"
        )

        val result = mutableListOf<String>()
        while (cursor.moveToNext()) {
            result.add(
                cursor.getString(
                    cursor.getColumnIndexOrThrow(PlaceContract.COLUMN_NAME_NAME)
                )
            )
        }
        cursor.close()
    }
}
