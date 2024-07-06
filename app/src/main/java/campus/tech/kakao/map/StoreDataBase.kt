package campus.tech.kakao.map

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Room

@Entity(tableName = "store")
data class StoreEntity (
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "location") val location: String
)

@Dao
interface StoreDao {
    @Query("SELECT * FROM store WHERE name LIKE :query OR location LIKE :query")
    fun search(query: String): List<StoreEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(store: StoreEntity)

    @Delete
    fun delete(store: StoreEntity)

}

@Database(entities = [StoreEntity::class], version = 1)
abstract class StoreDatabase : RoomDatabase() {
    abstract fun storeDao(): StoreDao
}

class SQLiteInit(private val context: Context) {
    private lateinit var database: StoreDatabase


    init {
        initDatabase()
    }

    private fun initDatabase() {
        context.applicationContext.deleteDatabase("store-database")
        database = Room.databaseBuilder(
            context.applicationContext,
            StoreDatabase::class.java,
            "store-database"
        ).build()
    }


    private val storeDao: StoreDao = database.storeDao()

    fun insertSampleData() {
        storeDao.insert(StoreEntity(name = "약국1", location = "서울시 성동구1"))
        storeDao.insert(StoreEntity(name = "약국2", location = "서울시 성동구2"))
        storeDao.insert(StoreEntity(name = "약국3", location = "서울시 성동구3"))
        storeDao.insert(StoreEntity(name = "약국4", location = "서울시 성동구4"))
        storeDao.insert(StoreEntity(name = "약국5", location = "서울시 성동구5"))
        storeDao.insert(StoreEntity(name = "약국6", location = "서울시 성동구6"))
        storeDao.insert(StoreEntity(name = "약국7", location = "서울시 성동구7"))
        storeDao.insert(StoreEntity(name = "약국8", location = "서울시 성동구8"))
        storeDao.insert(StoreEntity(name = "약국9", location = "서울시 성동구9"))
        storeDao.insert(StoreEntity(name = "약국10", location = "서울시 성동구10"))
        storeDao.insert(StoreEntity(name = "약국11", location = "서울시 성동구11"))
        storeDao.insert(StoreEntity(name = "약국12", location = "서울시 성동구12"))
        storeDao.insert(StoreEntity(name = "약국13", location = "서울시 성동구13"))
        storeDao.insert(StoreEntity(name = "약국14", location = "서울시 성동구14"))
        storeDao.insert(StoreEntity(name = "약국15", location = "서울시 성동구15"))
        storeDao.insert(StoreEntity(name = "약국16", location = "서울시 성동구16"))
        storeDao.insert(StoreEntity(name = "약국17", location = "서울시 성동구17"))
        storeDao.insert(StoreEntity(name = "약국18", location = "서울시 성동구18"))
        storeDao.insert(StoreEntity(name = "약국19", location = "서울시 성동구19"))
        storeDao.insert(StoreEntity(name = "약국20", location = "서울시 성동구20"))
        storeDao.insert(StoreEntity(name = "카페1", location = "서울시 성동구1"))
        storeDao.insert(StoreEntity(name = "카페2", location = "서울시 성동구2"))
        storeDao.insert(StoreEntity(name = "카페3", location = "서울시 성동구3"))
        storeDao.insert(StoreEntity(name = "카페4", location = "서울시 성동구4"))
        storeDao.insert(StoreEntity(name = "카페5", location = "서울시 성동구5"))
        storeDao.insert(StoreEntity(name = "카페6", location = "서울시 성동구6"))
        storeDao.insert(StoreEntity(name = "카페7", location = "서울시 성동구7"))
        storeDao.insert(StoreEntity(name = "카페8", location = "서울시 성동구8"))
        storeDao.insert(StoreEntity(name = "카페9", location = "서울시 성동구9"))
        storeDao.insert(StoreEntity(name = "카페10", location = "서울시 성동구10"))
    }
}
