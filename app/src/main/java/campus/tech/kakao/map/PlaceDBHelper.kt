package campus.tech.kakao.map

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log



class PlaceDBHelper(context:Context) : SQLiteOpenHelper(context, "place.db", null, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE " +
            "${PlaceContract.PlaceEntry.TABLE_NAME}( " +
            " ${PlaceContract.PlaceEntry.COLUMN_NAME} varchar(60) not null ," +
            " ${PlaceContract.PlaceEntry.COLUMN_LOCATION} varchar(255) not null, " +
            " ${PlaceContract.PlaceEntry.COLUMN_CATEGORY} varchar(30) );"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${PlaceContract.PlaceEntry.TABLE_NAME};")
        onCreate(db)
    }

    fun insertDummyData(){
        val name = "카페"
        val category = "카페"
        val location = "서울 성동구 성수동"
        for(i in 1..10){
            insertData(name + i, location + i, category)
        }
        readData()
    }

    fun insertData(name: String, location: String, category: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(PlaceContract.PlaceEntry.COLUMN_NAME, name)
            put(PlaceContract.PlaceEntry.COLUMN_LOCATION, location)
            put(PlaceContract.PlaceEntry.COLUMN_CATEGORY, category)
        }
        db.insert(PlaceContract.PlaceEntry.TABLE_NAME, null, values)
    }

    fun readData() :MutableList<Place>{
        val rDb = this.readableDatabase

        val cursor = rDb.rawQuery("SELECT * FROM ${PlaceContract.PlaceEntry.TABLE_NAME};", null)

        val result = mutableListOf<Place>()

        while (cursor.moveToNext()) {
            val place = Place(
                cursor.getString(
                    cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_NAME)
                ),
                cursor.getString(
                    cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_LOCATION)
                ),
                cursor.getString(
                    cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_CATEGORY)
                )
            )
            Log.d("readData", "이름 = ${place.name}, 위치 = ${place.location}, 분류 = ${place.category}")
            result.add(place)
        }

        cursor.close()
        return result
    }
}