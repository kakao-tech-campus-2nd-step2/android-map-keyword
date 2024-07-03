package campus.tech.kakao.map

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log

class MapModel(mContext: Context) {
    private val helper: MapDbHelper = MapDbHelper(mContext)

    init {
        helper.readableDatabase
    }

    fun insertLocation(location: Location) {
        val writableDb = helper.writableDatabase
        val content = ContentValues()
        content.put(MapContract.MapEntry.COLUMN_NAME_NAME, location.name)
        content.put(MapContract.MapEntry.COLUMN_NAME_CATEGORY, location.category)
        content.put(MapContract.MapEntry.COLUMN_NAME_ADDRESS, location.address)

        writableDb.insert(MapContract.MapEntry.TABLE_NAME, null, content)
    }

    fun searchLocation(locName: String, isExactMatch: Boolean): List<Location> {
        val readableDb = helper.readableDatabase

        val selection = "${MapContract.MapEntry.COLUMN_NAME_NAME} LIKE ?"
        val selectionArgs = arrayOf("%${locName}%")
        val cursor = readableDb.query(
            MapContract.MapEntry.TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        val res = mutableListOf<Location>()
        while (cursor.moveToNext()) {
            res.add(createLocationFromData(cursor))
        }
        cursor.close()
        return res
    }

    fun getAllLocation() : List<Location> {
        val readableDb = helper.readableDatabase
        val cursor = readableDb.query(
            MapContract.MapEntry.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        val res = mutableListOf<Location>()
        while (cursor.moveToNext()) {
            res.add(createLocationFromData(cursor))
        }
        cursor.close()
        return res
    }

    private fun createLocationFromData(cursor: Cursor): Location {
        val name = cursor.getString(cursor.getColumnIndexOrThrow(MapContract.MapEntry.COLUMN_NAME_NAME))
        val category = cursor.getString(cursor.getColumnIndexOrThrow(MapContract.MapEntry.COLUMN_NAME_CATEGORY))
        val address = cursor.getString(cursor.getColumnIndexOrThrow(MapContract.MapEntry.COLUMN_NAME_ADDRESS))

        return Location(name, category, address)
    }
}