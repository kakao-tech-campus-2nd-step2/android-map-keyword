package campus.tech.kakao.map

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

    @Query("SELECT * FROM store")
    fun getAll(): List<StoreEntity>
}

@Database(entities = [StoreEntity::class], version = 1)
abstract class StoreDatabase : RoomDatabase() {
    abstract fun storeDao(): StoreDao
}
