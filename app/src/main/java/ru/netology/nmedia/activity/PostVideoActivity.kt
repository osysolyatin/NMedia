package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityPostVideoBinding

class PostVideoActivity :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityPostVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent ?: return
        if (intent.action != Intent.ACTION_VIEW) return
        if (intent.type != "video") return

        val text = intent.getStringExtra(Intent.EXTRA_TEXT)
        if (text.isNullOrBlank()) {
            Snackbar.make(
                binding.root,
                R.string.error_empty_content,
                Snackbar.LENGTH_INDEFINITE
            ). setAction(android.R.string.ok) {
                finish()
            }.show()
        } else {
            binding.root.text = text  // обрабатываем пришедший текст
        }
    }
}