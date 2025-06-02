package com.myapplication.post.data.remote.api

import com.myapplication.post.data.remote.dto.PostDto
import retrofit2.http.GET

interface PostApiService {
    @GET("posts")
    suspend fun getPosts(): List<PostDto>
}