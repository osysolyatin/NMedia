package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import ru.netology.nmedia.data.impl.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewModel.PostViewModel
import java.text.DecimalFormat
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PostsAdapter(viewModel::onLikeClick, viewModel::onShareClick)

        binding.root.adapter = adapter
        viewModel.data.observe(this) {posts ->
            adapter.submitList(posts)

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


