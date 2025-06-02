package com.myapplication.post.di

import com.myapplication.post.data.local.database.PostDatabase
import com.myapplication.post.data.remote.api.PostApiService
import com.myapplication.post.data.repository.PostRepositoryImpl
import com.myapplication.post.domain.repository.PostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun providePostRepository(
        api: PostApiService,
        dao: PostDatabase
    ): PostRepository = PostRepositoryImpl(dao, api)
}
