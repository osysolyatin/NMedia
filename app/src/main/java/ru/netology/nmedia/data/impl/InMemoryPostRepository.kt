package ru.netology.nmedia.data.impl

import Post
import androidx.core.content.res.TypedArrayUtils.getString

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.R
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
        val likedPost = currentPost.copy(likedByMe = !currentPost.likedByMe)
        data.value = likedPost
    }
}