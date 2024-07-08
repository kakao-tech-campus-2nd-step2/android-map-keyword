package campus.tech.kakao.map.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import campus.tech.kakao.map.database.HistoryContract
import campus.tech.kakao.map.database.HistoryDBHelper
import campus.tech.kakao.map.database.PlaceContract
import campus.tech.kakao.map.database.PlaceDBHelper
import campus.tech.kakao.map.model.HistoryItem
import campus.tech.kakao.map.model.PlaceItem

class PlaceRepository(context: Context) {
    private val placeDBHelper: PlaceDBHelper = PlaceDBHelper(context)
    private val historyDBHelper: HistoryDBHelper = HistoryDBHelper(context, "history.db", null, 2)

    private val _places = MutableLiveData<List<PlaceItem>>()
    val places: LiveData<List<PlaceItem>> get() = _places

    private val _history = MutableLiveData<List<HistoryItem>>()
    val history: LiveData<List<HistoryItem>> get() = _history

    fun searchPlaces(keyword: String) {
        val selection = "${PlaceContract.COLUMN_CATEGORY} LIKE ?"
        val selectionArgs = arrayOf("%$keyword%")
        val db = placeDBHelper.readableDatabase
        val cursor = db.query(
            PlaceContract.TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        _places.value = cursorToPlaceList(cursor)
    }

    fun addHistory(name: String) {
        val db = historyDBHelper.writableDatabase
        val values = ContentValues()
        values.put(HistoryContract.COLUMN_NAME, name)
        db.insert(HistoryContract.TABLE_NAME, null, values)
        loadAllHistory()
    }

    fun removeHistory(name: String) {
        val db = historyDBHelper.writableDatabase
        val selection = "${HistoryContract.COLUMN_NAME} = ?"
        val selectionArgs = arrayOf(name)
        db.delete(HistoryContract.TABLE_NAME, selection, selectionArgs)
        loadAllHistory()
    }

    fun loadAllHistory() {
        val db = historyDBHelper.readableDatabase
        val cursor = db.query(
            HistoryContract.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )
        _history.value = cursorToHistoryList(cursor)
    }

    private fun cursorToPlaceList(cursor: Cursor?): List<PlaceItem> {
        val list = mutableListOf<PlaceItem>()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val name = cursor.getString(cursor.getColumnIndexOrThrow(PlaceContract.COLUMN_TITLE))
                val address = cursor.getString(cursor.getColumnIndexOrThrow(PlaceContract.COLUMN_LOCATION))
                val category = cursor.getString(cursor.getColumnIndexOrThrow(PlaceContract.COLUMN_CATEGORY))
                list.add(PlaceItem(name, address, category))
            }
            cursor.close()
        }
        return list
    }

    private fun cursorToHistoryList(cursor: Cursor?): List<HistoryItem> {
        val list = mutableListOf<HistoryItem>()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val name = cursor.getString(cursor.getColumnIndexOrThrow(HistoryContract.COLUMN_NAME))
                list.add(HistoryItem(name))
            }
            cursor.close()
        }
        return list
    }
}
