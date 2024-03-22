package com.example.ordersentences.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ordersentences.domain.model.Verb

@Database(
    entities = [Verb::class],
    version = 1,
)
abstract class OrderSentenceDatabase: RoomDatabase() {
    abstract val dao: VerbDao

    companion object {
        @Volatile
        private var INSTANCE: OrderSentenceDatabase? = null

        fun getDatabase(context: Context): OrderSentenceDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OrderSentenceDatabase::class.java,
                    "verb_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}