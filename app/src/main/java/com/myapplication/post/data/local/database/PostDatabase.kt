package com.myapplication.post.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.myapplication.post.data.local.entity.PostEntity

@Database(
    entities = [PostEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PostDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}