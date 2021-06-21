package com.giuseppesorce.vodafone.persistence.db

import android.content.Context
import androidx.room.*
import com.giuseppesorce.vodafone.persistence.db.dao.*
import com.giuseppesorce.vodafone.persistence.db.entities.*
import java.util.*


/**
 * @author Giuseppe Sorce
 */
@Database(
    entities = arrayOf(
        RHero::class

        ), version = 1, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MyDatabase : RoomDatabase() {


    abstract fun heroesDao(): HeroesDao


    companion object {

        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getAppDatabase(context: Context): MyDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "superheroes.db"
                )

                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE as MyDatabase
        }
        fun destroyInstance() {
            INSTANCE = null
        }
    }

}

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}