package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityNewPostBinding
import ru.netology.nmedia.viewModel.PostViewModel

class NewPostActivity : AppCompatActivity (){

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent ?: return
        val editText = intent.getStringExtra(Intent.EXTRA_TEXT)

        intent.putExtra(Intent.EXTRA_TEXT, editText)

        if (editText != null) {
            binding.edit.setText(editText)
        }
        binding.edit.requestFocus()


        binding.saveOkButton.setOnClickListener {
            onSaveOkButtonClicked(binding.edit.text?.toString())
        }
    }
    private fun onSaveOkButtonClicked (postContent: String?) {

        if (postContent.isNullOrBlank()) {
            setResult(Activity.RESULT_CANCELED)
        } else {
            val resultIntent = Intent()
            resultIntent.putExtra(POST_CONTENT_EXTRA_KEY, postContent)
            setResult(Activity.RESULT_OK, resultIntent)
        }
        finish()
    }

    private companion object {
        const val POST_CONTENT_EXTRA_KEY = "postContent"
    }

    object ResultContract : ActivityResultContract <String?,String?> () {

        override fun createIntent(context: Context, input: String?) : Intent {

        return Intent(context, NewPostActivity ::class.java) //сформировали явный Интент
    }
        override fun parseResult(resultCode: Int, intent: Intent?): String? {
            if (resultCode != Activity.RESULT_OK) return null
            intent ?: return null // Прошли все проверки
            return intent.getStringExtra(Intent.EXTRA_TEXT)
            }
        }

    }
