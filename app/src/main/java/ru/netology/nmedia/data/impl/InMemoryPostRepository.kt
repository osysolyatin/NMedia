package ru.netology.nmedia.data.impl

import Post
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.PostRepository

class InMemoryPostRepository : PostRepository {

    private val posts get() = checkNotNull(data.value) {
        "Data value should not be null"
    }

    override val data = MutableLiveData (
        List(1000) { index -> Post(
            id = index + 1L,
            author = "Олег",
            content = "Some random content No ${index+1}",
            published = "10 августа 2022 в 18:50",
            likes = 999,
            shares = 995
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
}