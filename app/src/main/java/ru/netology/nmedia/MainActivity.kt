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



