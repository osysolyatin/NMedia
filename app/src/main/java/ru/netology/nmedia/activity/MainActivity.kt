package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewModel.PostViewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PostsAdapter(viewModel)

        binding.container.adapter = adapter

        viewModel.data.observe(this) {posts ->
            adapter.submitList(posts)
        }

        viewModel.shareEvent.observe (this){ post ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"

                putExtra(Intent.EXTRA_TEXT,post?.content)
            }
            val shareIntent = Intent.createChooser(intent, getString(R.string.description_post_share))
            startActivity(shareIntent)
        }

        val activityLauncher = registerForActivityResult(
            NewPostActivity.ResultContract
        ) { postContent: String? ->
            println("postContent = $postContent")
            postContent?.let (viewModel :: onCreateOrEditPost)
        }
        binding.fabButton.setOnClickListener {
            val input = ""
            activityLauncher.launch(input)
        }
        viewModel.currentPost.observe(this) {
            activityLauncher.launch(it?.content)
        }
    }
}



