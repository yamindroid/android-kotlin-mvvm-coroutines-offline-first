package com.ymo.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ymo.data.local.db.Movie.MovieDao
import com.ymo.data.model.api.MovieItem

@Database(entities = [MovieItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}