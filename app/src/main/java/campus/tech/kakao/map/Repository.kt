package campus.tech.kakao.map

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Repository(context: Context):
    SQLiteOpenHelper(context, LocationContract.DATABASE_NAME, null, 1) {
        //CRUD
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(LocationContract.CREATE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(LocationContract.DROP_QUERY)
        onCreate(db)
    }

    fun insertData(location: Location){
        val values = ContentValues().apply {
            put(LocationContract.COLUMN_NAME, location.name)
            put(LocationContract.COLUMN_LOCATION, location.location)
            put(LocationContract.COLUMN_TYPE, location.type)
        }
        writableDatabase.insert(LocationContract.TABLE_NAME, null, values)
    }

    fun updateData( location: Location){
        val values = ContentValues().apply {
            put(LocationContract.COLUMN_LOCATION, location.location)
            put(LocationContract.COLUMN_TYPE, location.type)
        }
        writableDatabase.update(LocationContract.TABLE_NAME, values,
            "${LocationContract.COLUMN_NAME} = ?", arrayOf(location.name)
        )
    }

    fun deleteData(name: String){
        writableDatabase.delete(LocationContract.TABLE_NAME,
            "${LocationContract.COLUMN_NAME} = ?", arrayOf(name) )
    }

    fun getAll(): List<Location>{
        val locations = mutableListOf<Location>()
        val cursor = readableDatabase.query(
            LocationContract.TABLE_NAME,
            null, null, null, null, null, null
        )
        cursor?.use {
            while (it.moveToNext()) {
                val name = it.getString(it.getColumnIndexOrThrow(LocationContract.COLUMN_NAME))
                val location = it.getString(it.getColumnIndexOrThrow(LocationContract.COLUMN_LOCATION))
                val type = it.getString(it.getColumnIndexOrThrow(LocationContract.COLUMN_TYPE))
                locations.add(Location(name, location, type))
            }
        }
        return locations
    }

    fun dropTable(){
        writableDatabase.execSQL(
            LocationContract.DROP_QUERY
        )
        onCreate(writableDatabase)
    }

}