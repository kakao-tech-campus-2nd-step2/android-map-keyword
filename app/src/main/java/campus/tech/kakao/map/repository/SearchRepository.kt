package campus.tech.kakao.map.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import campus.tech.kakao.map.model.PlaceContract
import campus.tech.kakao.map.model.PlaceDBHelper

class SearchRepository(context: Context) {
    private val dbHelper = PlaceDBHelper(context)

    fun isDataExists(category: String): Boolean {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor: Cursor = db.query(
            PlaceContract.PlaceEntry.TABLE_NAME,
            arrayOf(PlaceContract.PlaceEntry.COLUMN_PLACE_CATEGORY),
            "${PlaceContract.PlaceEntry.COLUMN_PLACE_CATEGORY} = ?",
            arrayOf(category),
            null,
            null,
            null
        )
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    fun insertPlaceDummyData(name: String, address: String, category: String) {
        if (isDataExists(category)) {
            Log.d("ddangcong80", "Category $category already exists.")
            return
        }

        val db: SQLiteDatabase = dbHelper.writableDatabase
        val values = ContentValues()
        for (i in 1..15) {
            values.put(PlaceContract.PlaceEntry.COLUMN_PLACE_NAME, name + i)
            values.put(PlaceContract.PlaceEntry.COLUMN_PLACE_ADDRESS, address + i)
            values.put(PlaceContract.PlaceEntry.COLUMN_PLACE_CATEGORY, category)
            db.insert(PlaceContract.PlaceEntry.TABLE_NAME, null, values)
        }
    }

    fun getAllPlaces() {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.query(
                PlaceContract.PlaceEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
            )

            if (cursor != null) {
                val places = mutableListOf<String>()
                while (cursor.moveToNext()) {
                    val name =
                        cursor.getString(cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_PLACE_NAME))
                    val address =
                        cursor.getString(cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_PLACE_ADDRESS))
                    val category =
                        cursor.getString(cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_PLACE_CATEGORY))
                    places.add("Name: $name, Address: $address, Category: $category")
                }

                for (place in places) {
                    Log.d("ddangcong80", place)
                }
            }
        } catch (e: Exception) {
            Log.e("ddangcong80", "Error", e)
        } finally {
            cursor?.close()
            db.close()
        }
    }


}