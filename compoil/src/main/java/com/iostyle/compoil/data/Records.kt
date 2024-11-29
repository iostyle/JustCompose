package com.iostyle.compoil.data

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.iostyle.compoil.application.OilApplication

@Entity(tableName = "Records")
data class Records(
    @PrimaryKey val timestamp: Long,
    @ColumnInfo(name = "current_mileage") val currentMileage: Int,
    @ColumnInfo(name = "oil_injection") val oilInjection: Float
)

@Dao
interface RecordsDao {
    @Query("SELECT * FROM Records")
    fun queryAllRecords(): MutableList<Records>

    @Insert
    fun insertRecords(records: Records)

    @Delete
    fun deleteRecords(records: Records)
}

@Database(entities = [Records::class], version = 1)
abstract class RecordsDatabase : RoomDatabase() {
    abstract fun recordsDao(): RecordsDao

    companion object {
        private var instance: RecordsDatabase? = null
        fun get(context: Context): RecordsDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context, RecordsDatabase::class.java, "records_.db")
                    .fallbackToDestructiveMigration()
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                        }
                    })
                    .build()
            }
            return instance!!
        }
    }
}

suspend fun getCacheRecords(): MutableList<Records> {
    return RecordsDatabase.get(OilApplication.instance).recordsDao().queryAllRecords()
}

suspend fun addNewRecords(records: Records) {
    RecordsDatabase.get(OilApplication.instance).recordsDao().insertRecords(records)
}

suspend fun deleteRecords(records: Records) {
    RecordsDatabase.get(OilApplication.instance).recordsDao().deleteRecords(records)
}