package campus.tech.kakao.map.viewmodel

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import campus.tech.kakao.map.model.PlaceContract
import campus.tech.kakao.map.model.PlaceDBHelper

class SearchViewModel : ViewModel() {
    private var _searchText = MutableLiveData<String>()
    val searchText: LiveData<String>
        get() = _searchText

    fun clearSearchText() {
        _searchText.value = ""
    }

    fun insertPlaceDummyData(context: Context, name: String, address: String, category: String) {
        val dbHelper = PlaceDBHelper(context)
        val db: SQLiteDatabase = dbHelper.writableDatabase
        val values = ContentValues()
        for (i: Int in 1..15) {
            values.put(PlaceContract.PlaceEntry.COLUMN_PLACE_NAME, name + i)
            values.put(PlaceContract.PlaceEntry.COLUMN_PLACE_ADDRESS, address + i)
            values.put(PlaceContract.PlaceEntry.COLUMN_PLACE_CATEGORY, category)
            db.insert(PlaceContract.PlaceEntry.TABLE_NAME, null, values)
        }
        db.close()
    }

    fun getAllPlaces(context: Context): List<String> {
        val dbHelper = PlaceDBHelper(context)
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor: Cursor = db.query(
            PlaceContract.PlaceEntry.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

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
        cursor.close()
        db.close()

        for (place in places) {
            Log.d("ddangcong80", place)
        }

        return places
    }
}