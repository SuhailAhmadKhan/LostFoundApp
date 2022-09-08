package com.example.myshoppal.ui.activities

import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.myshoppal.R
import com.google.firebase.auth.FirebaseAuth

@Suppress("DEPRECATION")
class ForgotPasswordActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val forgot=findViewById<Button>(R.id.btn_submit_forgot)
        forgot.setOnClickListener {
            val newEmail=findViewById<TextView>(R.id.et_email_forgot)
            val email: String = newEmail.text.toString().trim { it <= ' ' }
            if (email.isEmpty()) {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
            } else {
                showProgressDialog(resources.getString(R.string.please_wait))
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        hideProgressDialog()

                        if (task.isSuccessful) {
                            Toast.makeText(
                                this@ForgotPasswordActivity,
                                resources.getString(R.string.email_sent_success),
                                Toast.LENGTH_LONG
                            ).show()

                            finish()
                        } else {
                            showErrorSnackBar(task.exception!!.message.toString(), true)
                        }
                    }
            }
        }
        val toolBar: androidx.appcompat.widget.Toolbar
        toolBar = findViewById(R.id.toolbar_forgot_password_activity)
        fun setupActionBar() {
            setSupportActionBar(toolBar)
            val actionBar = supportActionBar
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true)
                actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
            }
            toolBar.setNavigationOnClickListener { onBackPressed() }
        }
        setupActionBar()
    }
}