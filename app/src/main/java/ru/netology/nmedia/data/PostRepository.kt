package ru.netology.nmedia.data

import Post
import androidx.lifecycle.LiveData

interface PostRepository {

    val data: LiveData<List<Post>>
    fun like(postId: Long)
    fun shared(postId: Long)
}