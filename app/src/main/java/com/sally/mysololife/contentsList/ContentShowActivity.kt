package com.sally.mysololife.contentsList

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sally.mysololife.R

class ContentShowActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_content_show)
        val getUrl = intent.getStringExtra("url")
        Toast.makeText(this,getUrl,Toast.LENGTH_LONG).show()

        val webView : WebView =findViewById(R.id.webView)
        webView.loadUrl(getUrl.toString())

    }
}