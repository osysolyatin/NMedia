package ru.netology.nmedia

import Post
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.ui.theme.NMediaTheme
import java.text.DecimalFormat
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 0L,
            author = getString(R.string.author),
            content = getString(R.string.post_text),
            published = getString(R.string.published),
            likes = 999,
            shares  = 995
        )

        with (binding){
            author.text = post.author
            published.text = post.published
            postText.text = post.content
            likeCountText.text = post.likes.toString()
            shareCountText.text = post.shares.toString()
        }

        binding.likeIcon.setOnClickListener {
            post.likedByMe = !post.likedByMe
            val imageResId =
                if (post.likedByMe) R.drawable.ic_like_filled_24 else R.drawable.ic_like_border_24
            binding.likeIcon.setImageResource(imageResId)

            if (post.likedByMe) post.likes += 1 else post.likes -= 1
            binding.likeCountText.text = countViews(post.likes.toLong())
        }

        binding.shareIcon.setOnClickListener {
                post.shares ++
            binding.shareCountText.text = countViews(post.shares.toLong())

        }
    }
}

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

@Composable
fun Greeting(name: String) {
    Text(text = "$name!")

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NMediaTheme {
        Greeting("NMedia")
    }
}

