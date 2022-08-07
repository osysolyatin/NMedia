package ru.netology.nmedia

import Post
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.annotation.DrawableRes
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewModel.PostViewModel
import java.text.DecimalFormat
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

class MainActivity : ComponentActivity() {

    private val viewModel = PostViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.data.observe(this) { post ->
            binding.render(post)
        }

        binding.likeIcon.setOnClickListener {
            viewModel.onLikeClick()
        }

        binding.shareIcon.setOnClickListener {
            viewModel.onShareClick()
        }

    }
}

private fun ActivityMainBinding.render(post: Post) {
    author.text = post.author
    postText.text = post.content
    published.text = post.published
    likeIcon.setImageResource(getLikeIconResId(post.likedByMe))
    likeCountText.text = countViews(post.likes.toLong())
    shareCountText.text = countViews(post.shares.toLong())
}

@DrawableRes
private fun getLikeIconResId(liked: Boolean) =
    if (liked) R.drawable.ic_like_filled_24 else R.drawable.ic_like_border_24

fun countViews(count:Long): String{
    val array = arrayOf(' ', 'k', 'M', 'B', 'T', 'P', 'E')
    val value = floor(log10(count.toDouble())).toInt()
    val  base = value / 3
    return if (value >= 3 && base < array.size) {
        DecimalFormat("#0.0").format(count/ 10.0.pow((base * 3).toDouble())) + array[base]
    } else {
        DecimalFormat("#,##0").format(count)
    }
}


