package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.databinding.ActivityShareBinding

class ShareActivity :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityShareBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent ?: return
        if (intent.action != Intent.ACTION_SEND) return
        if (intent.type != "text/plain") return

        val text = intent.getStringExtra(Intent.EXTRA_TEXT)
        if (text.isNullOrBlank()) {
            Snackbar.make(
                binding.root,
                "Присланный текст пустой",
                Snackbar.LENGTH_INDEFINITE
            ). setAction(android.R.string.ok) {
                finish()
            }.show()
        } else {
            binding.root.text = text  // обрабатываем пришеедший текст
        }
    }
}