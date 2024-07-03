package campus.tech.kakao.map.Model.Datasource.Local

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import campus.tech.kakao.map.Model.Place
import campus.tech.kakao.map.Util.PlaceCategory
import campus.tech.kakao.map.Util.PlaceContract


class DbHelper(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context,name,factory,version) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(PlaceContract.PlaceEntry.SQL_CREATE_TABLE)
        db.execSQL(PlaceContract.FavoriteEntry.SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(PlaceContract.PlaceEntry.SQL_DROP_TABLE)
        onCreate(db)
    }

    fun makeMock(){
        val db = writableDatabase
        for(i in 0..20){
            val place = Place("카페$i","복현${i}동", PlaceCategory.CAFE.ordinal)
            insertPlace(place)
        }
    }

    private fun insertPlace(place : Place){
        val db = writableDatabase
        val values = ContentValues().apply {
            this.put(PlaceContract.PlaceEntry.COLUMN_NAME,place.name)
            this.put(PlaceContract.PlaceEntry.COLUMN_ADDRESS,place.address)
            this.put(PlaceContract.PlaceEntry.COLUMN_CATEGORY,place.category)
        }

        db.insert(PlaceContract.PlaceEntry.TABLE_NAME,null,values)
    }

    fun insertFavorite(place : String){
        val db = writableDatabase
        val cursor = getCursorByName(place)
        val place = getPlaceByCursor(cursor)

        val values = ContentValues().apply {
            this.put(PlaceContract.PlaceEntry.COLUMN_NAME,place.name)
            this.put(PlaceContract.PlaceEntry.COLUMN_ADDRESS,place.address)
            this.put(PlaceContract.PlaceEntry.COLUMN_CATEGORY,place.category)
        }

        Log.d("db",values.toString())

        db.insert(PlaceContract.FavoriteEntry.TABLE_NAME,null,values)
    }

    fun getAllPlace() : Cursor{
        val db = readableDatabase
        return db.rawQuery("SELECT * FROM ${PlaceContract.PlaceEntry.TABLE_NAME}",null)
    }

    fun deletePlace(name : String){
        val db = writableDatabase
        db.delete(PlaceContract.PlaceEntry.TABLE_NAME,"${PlaceContract.PlaceEntry.COLUMN_NAME}=?",arrayOf(name))
    }

    fun deleteFavorite(name : String){
        val db = writableDatabase
        db.delete(PlaceContract.FavoriteEntry.TABLE_NAME,"${PlaceContract.PlaceEntry.COLUMN_NAME}=?",arrayOf(name))
    }

    fun getCursorByName(name: String) : Cursor{
        val db = readableDatabase

        return db.rawQuery("SELECT * FROM ${PlaceContract.PlaceEntry.TABLE_NAME} WHERE name=?",arrayOf(name))
    }

    fun getSimilarCursorByName(name: String) : Cursor{
        val db = readableDatabase
        val selection = "${PlaceContract.PlaceEntry.COLUMN_NAME} LIKE ?"
        val selectionArgs = arrayOf("%$name%")

        return db.query(
            PlaceContract.PlaceEntry.TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
    }

    private fun getPlaceByCursor(cursor: Cursor): Place {
        cursor.moveToFirst()
        return Place(
            cursor.getString(
                cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_NAME)
            ),
            cursor.getString(
                cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_ADDRESS)
            ),
            cursor.getInt(
                cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_CATEGORY)
            )
        )
    }

    fun selectAllFavorite() : Cursor{
        val db = readableDatabase

        return db.rawQuery("SELECT * FROM ${PlaceContract.FavoriteEntry.TABLE_NAME}",null)
    }
}