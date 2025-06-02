package com.myapplication.post.di

import android.content.Context
import androidx.room.Room
import com.myapplication.post.data.local.database.PostDao
import com.myapplication.post.data.local.database.PostDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext ctx: Context
    ): PostDatabase {
        return Room.databaseBuilder(
            ctx.applicationContext,
            PostDatabase::class.java,
            "app.db"
        ).build()
    }

    @Provides
    @Singleton
    fun providePostDao(db: PostDatabase): PostDao {
        return db.postDao()
    }
}