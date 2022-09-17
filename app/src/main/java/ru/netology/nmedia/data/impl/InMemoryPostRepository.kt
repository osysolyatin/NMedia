package ru.netology.nmedia.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository

class InMemoryPostRepository : PostRepository {

    private var nextId = GENERATED_POSTS_COUNT.toLong()

    private val posts get() = checkNotNull(data.value) {
        "Data value should not be null"
    }

    override val data = MutableLiveData (
        List(GENERATED_POSTS_COUNT) { index -> Post(
            id = index + 1L,
            author = "Олег",
            content = "Post content No ${index+1}",
            published = "10 августа 2022 в 18:50",
            likes = 999,
            shares = 995,
            video = null
        )
        }
    )

    override fun like(postId: Long) {
        data.value = posts.map {
            if (it.id != postId) it
            else {
                val liked = !it.likedByMe
                if (liked) it.likes++ else it.likes--
                it.copy( likedByMe = liked, likes = it.likes)
            }
        }
    }

    override fun shared(postId: Long) {
        data.value = posts.map {
            if (it.id != postId) it
            else it.copy(shares = it.shares +1)
        }
    }

    override fun delete(postId: Long) {
        data.value = posts.filter { it.id != postId }
    }

    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) insert (post) else update (post)
    }

    private fun insert(post: Post) {
        data.value = listOf(
            post.copy (id = ++nextId)
        ) + posts
    }

    private fun update(post: Post) {
        data.value = posts.map {
            if (it.id == post.id) post else it
        }
    }

    private companion object {
        const val GENERATED_POSTS_COUNT = 1000
    }

}