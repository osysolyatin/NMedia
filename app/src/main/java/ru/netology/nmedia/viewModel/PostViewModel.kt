package ru.netology.nmedia.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.Post
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository

class PostViewModel : ViewModel(), PostInteractionListener {

    private val repository : PostRepository = InMemoryPostRepository()
    val data by repository::data

    val currentPost = MutableLiveData<Post?> (null)

    fun onSaveButtonClicked (content: String) {
        if (content.isBlank()) return
        val post = currentPost.value?.copy(
            content = content
        ) ?:
            Post (
            id = PostRepository.NEW_POST_ID,
            author = "Me",
            content = content,
            published = "24.12.2021"
                )
        repository.save(post)
        currentPost.value = null
    }

    fun onCancelEditButtonClicked() {
        currentPost.value = null
    }

    val shareEvent = MutableLiveData<Post?>(null)

    // region PostInteractionListener

    override fun onLikeClicked(post: Post) = repository.like(post.id)

    override fun onShareClicked(post: Post) {
        repository.shared(post.id)
        shareEvent.value = post
    }

    override fun onRemoveClicked(post: Post) = repository.delete(post.id)

    override fun onEditClicked(post: Post) {
        currentPost.value = post
    }

    fun onCreateNewPost (newPostContent: String) {
        if (newPostContent.isNullOrBlank()) return
        val post = currentPost.value?.copy(
            content = newPostContent
        ) ?:
        Post (
            id = PostRepository.NEW_POST_ID,
            author = "Me",
            content = newPostContent,
            published = "24.12.2021"
        )
        repository.save(post)
        currentPost.value = null
    }

    // endregion PostInteractionListener
}