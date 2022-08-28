package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityNewPostBinding

class NewPostActivity : AppCompatActivity (){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.edit.setTextColor(resources.getColor(R.color.black,theme))
        binding.edit.setText(textToBeEdited)
        binding.edit.requestFocus()


        binding.saveOkButton.setOnClickListener {
            onSaveOkButtonClicked(binding.edit.text?.toString())
        }
    }
    private fun onSaveOkButtonClicked (postContent: String?) {
        println("postContent from OnSaveButton - $postContent")
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
        var textToBeEdited = ""
    }

    object ResultContract : ActivityResultContract <String?,String?> () {

        override fun createIntent(context: Context, input: String?) : Intent {
            textToBeEdited = "Enter new post here"
            if (input != null) {
                textToBeEdited = input
            }
        return Intent(context, NewPostActivity ::class.java) //сформировали явный Интент
    }
        override fun parseResult(resultCode: Int, intent: Intent?): String? {
            if (resultCode != Activity.RESULT_OK) return null
            intent ?: return null // Прошли все проверки
            return intent.getStringExtra(POST_CONTENT_EXTRA_KEY)
            }
        }

    }
