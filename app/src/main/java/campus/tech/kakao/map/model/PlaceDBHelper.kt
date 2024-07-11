package campus.tech.kakao.map.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class PlaceDBHelper(context: Context) : SQLiteOpenHelper(context, "placedb", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(PlaceContract.PlaceEntry.CREATE_QUERY)
        db?.execSQL(PlaceContract.SavePlaceEntry.CREATE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(PlaceContract.PlaceEntry.DROP_QUERY)
        db?.execSQL(PlaceContract.SavePlaceEntry.DROP_QUERY)
        onCreate(db)
    }

    private fun isDataExists(category: String): Boolean {
        val db: SQLiteDatabase = this.readableDatabase
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

        val db: SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        for (i in 1..15) {
            values.put(PlaceContract.PlaceEntry.COLUMN_PLACE_NAME, name + i)
            values.put(PlaceContract.PlaceEntry.COLUMN_PLACE_ADDRESS, address + i)
            values.put(PlaceContract.PlaceEntry.COLUMN_PLACE_CATEGORY, category)
            db.insert(PlaceContract.PlaceEntry.TABLE_NAME, null, values)
        }
        db.close()
    }

    fun getSearchPlaces(placeCategory: String): MutableList<Place> {
        val db: SQLiteDatabase = this.readableDatabase
        val places = mutableListOf<Place>()
        var cursor: Cursor? = null
        try {
            val selection = "${PlaceContract.PlaceEntry.COLUMN_PLACE_CATEGORY} LIKE ?"
            val selectionArgs = arrayOf("$placeCategory%")

            cursor = db.query(
                PlaceContract.PlaceEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
            )

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val name =
                        cursor.getString(cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_PLACE_NAME))
                    val address =
                        cursor.getString(cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_PLACE_ADDRESS))
                    val category =
                        cursor.getString(cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_PLACE_CATEGORY))

                    places.add(Place(name, address, category))
                }
            }
        } catch (e: Exception) {
            Log.e("ddangcong80", "Error", e)
        } finally {
            cursor?.close()
            db.close()
        }

        return places
    }


    fun savePlaces(placeName: String) {
        val db: SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(PlaceContract.SavePlaceEntry.COLUMN_PLACE_NAME, placeName)

        val cursor = db.query(
            PlaceContract.SavePlaceEntry.TABLE_NAME,
            arrayOf(PlaceContract.SavePlaceEntry.COLUMN_PLACE_NAME),
            "${PlaceContract.SavePlaceEntry.COLUMN_PLACE_NAME} = ?",
            arrayOf(placeName),
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            db.delete(
                PlaceContract.SavePlaceEntry.TABLE_NAME,
                "${PlaceContract.SavePlaceEntry.COLUMN_PLACE_NAME} = ?",
                arrayOf(placeName)
            )
        }
        cursor.close()
        db.insert(PlaceContract.SavePlaceEntry.TABLE_NAME, null, values)
        db.close()
    }

    fun showSavePlace(): MutableList<SavePlace> {
        val db: SQLiteDatabase = this.readableDatabase
        val savePlaces = mutableListOf<SavePlace>()
        var cursor: Cursor? = null
        try {
            cursor = db.query(
                PlaceContract.SavePlaceEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
            )

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val name =
                        cursor.getString(cursor.getColumnIndexOrThrow(PlaceContract.SavePlaceEntry.COLUMN_PLACE_NAME))

                    savePlaces.add(SavePlace(name))
                }
            }
        } catch (e: Exception) {
            Log.e("ddangcong80", "Error", e)
        } finally {
            cursor?.close()
            db.close()
        }

        return savePlaces
    }

    fun deleteSavedPlace(savedPlaceName: String) {
        val db: SQLiteDatabase = this.writableDatabase

        val cursor = db.query(
            PlaceContract.SavePlaceEntry.TABLE_NAME,
            arrayOf(PlaceContract.SavePlaceEntry.COLUMN_PLACE_NAME),
            "${PlaceContract.SavePlaceEntry.COLUMN_PLACE_NAME} = ?",
            arrayOf(savedPlaceName),
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            db.delete(
                PlaceContract.SavePlaceEntry.TABLE_NAME,
                "${PlaceContract.SavePlaceEntry.COLUMN_PLACE_NAME} = ?",
                arrayOf(savedPlaceName)
            )
        }
        cursor.close()
        db.close()
    }
}