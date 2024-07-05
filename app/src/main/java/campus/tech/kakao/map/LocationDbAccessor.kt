package campus.tech.kakao.map

import android.content.ContentValues
import android.content.Context
import android.util.Log
import campus.tech.kakao.map.Contract.LocationEntry

class LocationDbAccessor(private val dbHelper : LocationDbHelper) {

    fun insertLocation(title: String, address: String, category: String): Long {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(LocationEntry.COLUMN_NAME_TITLE, title)
            put(LocationEntry.COLUMN_NAME_ADDRESS, address)
            put(LocationEntry.COLUMN_NAME_CATEGORY, category)
        }

        return db.insert(LocationEntry.TABLE_NAME, null, values)
    }

    fun getLocationAll(): MutableList<Location> {
        val db = dbHelper.readableDatabase

        val projection = arrayOf(
            LocationEntry.COLUMN_NAME_TITLE,
            LocationEntry.COLUMN_NAME_ADDRESS,
            LocationEntry.COLUMN_NAME_CATEGORY
        )

//        val selection = "${LocationEntry.COLUMN_NAME_CATEGORY} = ?"
//        val selectionArgs = arrayOf(category)

        val sortOrder = "${LocationEntry.COLUMN_NAME_TITLE} ASC"

        val cursor = db.query(
            LocationEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            sortOrder
        )

        val results = mutableListOf<Location>()
        with(cursor) {
            while (moveToNext()) {
                val title = getString(getColumnIndexOrThrow(LocationEntry.COLUMN_NAME_TITLE))
                val address = getString(getColumnIndexOrThrow(LocationEntry.COLUMN_NAME_ADDRESS))
                val category = getString(getColumnIndexOrThrow(LocationEntry.COLUMN_NAME_CATEGORY))
                results.add(Location(title, address, category))
            }
        }
        cursor.close()
        return results
    }

    fun insertSavedLocation(title: String): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(Contract.SavedLocationEntry.COLUMN_NAME_TITLE, title)
        }
        Log.d("jieun", "저장완료")
        return db.insert(Contract.SavedLocationEntry.TABLE_NAME, null, values)
    }

    fun getSavedLocationAll(): MutableList<SavedLocation> {
        val db = dbHelper.readableDatabase

        val projection = arrayOf(
            Contract.SavedLocationEntry.COLUMN_NAME_TITLE,
        )
        val sortOrder = "${Contract.SavedLocationEntry.COLUMN_NAME_TITLE} ASC"
        val cursor = db.query(
            Contract.SavedLocationEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            sortOrder
        )

        val results = mutableListOf<SavedLocation>()
        with(cursor) {
            while (moveToNext()) {
                val title = getString(getColumnIndexOrThrow(Contract.SavedLocationEntry.COLUMN_NAME_TITLE))
                results.add(SavedLocation(title))
            }
        }
        cursor.close()
        return results
    }

    fun deleteSavedLocation(title: String) {
        val db = dbHelper.writableDatabase

        val selection = "title = ?"
        val selectionArgs = arrayOf(title)

        db.delete("SavedLocation", selection, selectionArgs)
    }
}
