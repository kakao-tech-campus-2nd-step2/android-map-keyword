package campus.tech.kakao.map.DataRepository

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import campus.tech.kakao.map.DBHelper.SearchDBHelper
import campus.tech.kakao.map.DataContract.SearchDataContract
import campus.tech.kakao.map.Data.SearchData

class SearchDataRepository(context: Context) {
    private val db: SearchDBHelper = SearchDBHelper(context)

    fun insertSearchData(data: SearchData) {
        val wDb = db.writableDatabase
        val cv = ContentValues().apply {
            put(SearchDataContract.TABLE_COLUMN_NAME, data.name)
            put(SearchDataContract.TABLE_COLUMN_CATEGORY, data.category)
            put(SearchDataContract.TABLE_COLUMN_ADDRESS, data.address)
        }
        wDb.insert(SearchDataContract.TABLE_NAME, null, cv)
    }

    fun getSearchDataList(): List<SearchData> {
        val rDb = db.readableDatabase
        val cursor: Cursor =
            rDb.query(SearchDataContract.TABLE_NAME, null, null, null, null, null, null)
        val searchDataList = mutableListOf<SearchData>()

        while (cursor.moveToNext()) {
            val name =
                cursor.getString(cursor.getColumnIndexOrThrow(SearchDataContract.TABLE_COLUMN_NAME))
            val category =
                cursor.getString(cursor.getColumnIndexOrThrow(SearchDataContract.TABLE_COLUMN_CATEGORY))
            val address =
                cursor.getString(cursor.getColumnIndexOrThrow(SearchDataContract.TABLE_COLUMN_ADDRESS))
            searchDataList.add(SearchData(name, category, address))
        }
        cursor.close()

        return searchDataList
    }

    @SuppressLint("Recycle")
    fun getResultDataList(data: String): List<SearchData> {
        val rDb = db.readableDatabase
        val resultDataList = mutableListOf<SearchData>()
        val query =
            "Select * from ${SearchDataContract.TABLE_NAME} WHERE ${SearchDataContract.TABLE_COLUMN_NAME} Like '%$data%'"
        val cursor: Cursor = rDb.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val name =
                cursor.getString(cursor.getColumnIndexOrThrow(SearchDataContract.TABLE_COLUMN_NAME))
            val category =
                cursor.getString(cursor.getColumnIndexOrThrow(SearchDataContract.TABLE_COLUMN_CATEGORY))
            val address =
                cursor.getString(cursor.getColumnIndexOrThrow(SearchDataContract.TABLE_COLUMN_ADDRESS))
            resultDataList.add(SearchData(name, category, address))
        }
        cursor.close()

        return resultDataList
    }
}