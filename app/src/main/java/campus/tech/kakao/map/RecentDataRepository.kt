package campus.tech.kakao.map

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor

class RecentDataRepository(context: Context){
    private val db: RecentDBHelper = RecentDBHelper(context)
    private val wDb = db.writableDatabase
    private val rDb = db.readableDatabase

    fun insertSearchData(data: String){
        val cv = ContentValues().apply{
            put(RecentDataContract.TABLE_COLUMN_NAME,data)
        }
        wDb.insert(RecentDataContract.TABLE_NAME,null,cv)
    }

    fun getSearchDataList(): List<String>{
        val cursor: Cursor = rDb.query(RecentDataContract.TABLE_NAME,null,null,null,null,null,null)
        val recentDataList = mutableListOf<String>()

        while(cursor.moveToNext()){
            val name = cursor.getString(cursor.getColumnIndexOrThrow(SearchDataContract.TABLE_COLUMN_NAME))
            recentDataList.add(name)
        }
        cursor.close()

        return recentDataList
    }

    fun deleteSearchData(data:String){
        wDb.delete(RecentDataContract.TABLE_NAME,RecentDataContract.TABLE_COLUMN_NAME, arrayOf(data))
    }
}