package ru.netology.nmedia.data.impl

import Post
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.countViews
import ru.netology.nmedia.databinding.PostBinding

typealias onPostLikeClicked = (Post) -> Unit

internal class PostsAdapter(

    private val onLikeClick: onPostLikeClicked,
    private val onShareClick: onPostLikeClicked

) : ListAdapter <Post, PostsAdapter.ViewHolder> (DiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostBinding.inflate(inflater, parent, false)
        return ViewHolder(binding,onLikeClick, onShareClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder (
        private val binding: PostBinding, onLikeClick: onPostLikeClicked, onShareClick: onPostLikeClicked /* = (Post) -> kotlin.Unit */
    ): RecyclerView.ViewHolder (binding.root) {

        private lateinit var post: Post

        init {
            binding.likeIcon.setOnClickListener { onLikeClick(post) }
            binding.shareIcon.setOnClickListener { onShareClick(post) }
        }

        fun bind (post: Post) {
            this.post = post
            with(binding){
                author.text = post.author
                postText.text = post.content
                published.text = post.published
                likeIcon.setImageResource(getLikeIconResId(post.likedByMe))
                likeCountText.text = countViews(post.likes.toLong())
                shareCountText.text = countViews(post.shares.toLong())
            }
        }

        @DrawableRes
        private fun getLikeIconResId(liked: Boolean) =
            if (liked) R.drawable.ic_like_filled_24 else R.drawable.ic_like_border_24
    }

    private object DiffCallback : DiffUtil.ItemCallback<Post>(){
        override fun areItemsTheSame(oldItem: Post, newItem: Post) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem
    }
}


