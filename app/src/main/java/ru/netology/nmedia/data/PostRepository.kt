package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import ru.netology.nmedia.Post

interface PostRepository {

    val data: LiveData<List<Post>>
    fun like(postId: Long)
    fun shared(postId: Long)
    fun delete(postId: Long)
    fun save (post: Post)

    companion object {
        const val NEW_POST_ID = 0L
    }
}