package ru.netology.nmedia.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.SingleLiveEvent
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.FilePostRepository

class PostViewModel (
    application: Application
) : AndroidViewModel(application),
    PostInteractionListener {

    private val repository : PostRepository = FilePostRepository(application)

    val data by repository::data

    private val currentPost = MutableLiveData<Post?> (null)
    val shareEvent = SingleLiveEvent<Post>()
    val editEvent = SingleLiveEvent<Post>()
    val videoEvent = SingleLiveEvent<Post>()

    // region PostInteractionListener

    override fun onLikeClicked(post: Post) = repository.like(post.id)

    override fun onShareClicked(post: Post) {
        repository.shared(post.id)
        shareEvent.value = post
    }

    override fun onRemoveClicked(post: Post) = repository.delete(post.id)

    override fun onEditClicked(post: Post) {
        editEvent.value = post
        currentPost.value = post
    }

    fun onCreateOrEditPost (newPostContent: String) {
        if (newPostContent.isBlank()) return
        val post = currentPost.value?.copy(
            content = newPostContent
        ) ?:
        Post (
            id = PostRepository.NEW_POST_ID,
            author = "Me",
            content = newPostContent,
            video = "https://youtu.be/ajhWG7Se6GA",
            published = "24.12.2021"
        )
        repository.save(post)
        currentPost.value = null
    }

    override fun onVideoClicked (post: Post) {
        println("Нажпли на видео поста № ${post.id}")
        videoEvent.value = post
//        return Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/ajhWG7Se6GA"))
    }

    // endregion PostInteractionListener
}
