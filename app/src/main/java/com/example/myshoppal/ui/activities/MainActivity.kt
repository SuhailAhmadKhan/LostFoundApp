package com.example.myshoppal.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import com.example.myshoppal.R

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val btn_lost: Button
        btn_lost = findViewById(R.id.btn_lost)
        btn_lost.setOnClickListener {
            val intent = Intent(this@MainActivity, LostActivity::class.java)
            startActivity(intent)
            finish()
        }
        setupActionBar()

    }
    private fun setupActionBar() {
        val toolBar: androidx.appcompat.widget.Toolbar
        toolBar = findViewById(R.id.toolbar_main_activity)

        setSupportActionBar(toolBar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        }
        toolBar.setNavigationOnClickListener {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}