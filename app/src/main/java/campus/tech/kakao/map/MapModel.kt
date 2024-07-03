package campus.tech.kakao.map

import android.content.ContentValues
import android.content.Context
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
            val name = cursor.getString(cursor.getColumnIndexOrThrow(MapContract.MapEntry.COLUMN_NAME_NAME))
            val category = cursor.getString(cursor.getColumnIndexOrThrow(MapContract.MapEntry.COLUMN_NAME_CATEGORY))
            val address = cursor.getString(cursor.getColumnIndexOrThrow(MapContract.MapEntry.COLUMN_NAME_ADDRESS))

            res.add(Location(name, category, address))
        }
        cursor.close()
        return res
    }
}