package ru.netology.nmedia.data

import Post
import androidx.lifecycle.LiveData

interface PostRepository {

    val data: LiveData<Post>
    fun like()
    fun shared()
}