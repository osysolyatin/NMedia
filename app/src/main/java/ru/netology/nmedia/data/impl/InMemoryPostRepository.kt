package ru.netology.nmedia.data.impl

import Post
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.PostRepository

class InMemoryPostRepository : PostRepository {
    override val data = MutableLiveData<Post>(
        Post(
            id = 0L,
            author = "Олег",
            content = "Привет. Это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн маркетингу.",
            published = "04 августа 2022 в 18:50",
            likes = 999,
            shares = 995
        )
    )


    override fun like() {
        val currentPost = checkNotNull(data.value) {
            "Data value should not be null"
        }
        val liked = !currentPost.likedByMe
        if (liked) currentPost.likes++ else currentPost.likes--
        val likedPost = currentPost.copy(likedByMe = liked, likes = currentPost.likes)
        data.value = likedPost
    }

    override fun shared() {
        val currentPost = checkNotNull(data.value) {
            "Data value should not be null"
        }
        val sharedPost = currentPost.copy(shares = currentPost.shares +1)
        data.value = sharedPost
    }
}