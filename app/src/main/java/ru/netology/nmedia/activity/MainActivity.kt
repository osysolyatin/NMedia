package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.util.hideKeyboard
import ru.netology.nmedia.util.showKeyboard
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

        binding.saveButton.setOnClickListener {
            with(binding.contentEditText) {
                val content = text.toString()
                viewModel.onSaveButtonClicked(content)
            }
        }

        binding.cancelEditButton.setOnClickListener {
            binding.editModeDescriptionGroup.visibility = View.GONE
            with(binding.contentEditText) {
                text.clear()
                clearFocus()
                hideKeyboard()
                viewModel.onCancelEditButtonClicked()
            }
        }

        viewModel.currentPost.observe(this) { currentPost ->
            with(binding.contentEditText) {
                val content = currentPost?.content
                setText(content) /*затирание текста*/
                if (content != null) {
                    binding.editModeDescriptionGroup.visibility = View.VISIBLE
                    binding.postToBeEdited.text = currentPost.content
                    requestFocus()
                    showKeyboard()
                } else {
                    binding.editModeDescriptionGroup.visibility = View.GONE
                    clearFocus()
                    hideKeyboard()
                }
            }
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
            postContent?.let (viewModel :: onCreateNewPost)
        }
        binding.fabButton.setOnClickListener {
            activityLauncher.launch(Unit)
        }
    }
}



