package com.example.myshoppal.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.myshoppal.R
import com.google.firebase.auth.FirebaseAuth


@Suppress("DEPRECATION")
class LoginActivity : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val register: TextView
        register = findViewById(R.id.tv_register)
        register.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
        val login:Button
        login=findViewById(R.id.btn_login)
        login.setOnClickListener(this)
        val forgotPass:TextView
        forgotPass=findViewById(R.id.tv_forgot_password)
        forgotPass.setOnClickListener(this)
        val Register:TextView
        Register=findViewById(R.id.tv_register)
        Register.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {

                R.id.tv_forgot_password -> {
                    val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
                    startActivity(intent)
                }

                R.id.btn_login -> {

                    // START
                    logInRegisteredUser()
                    // END
                }

                R.id.tv_register -> {
                    // Launch the register screen when the user clicks on the text.
                    val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun validateLoginDetails(): Boolean {
        return when {
            TextUtils.isEmpty(R.id.et_email.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }
            TextUtils.isEmpty(R.id.et_password.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }
            else -> {
                true
            }
        }
    }

    private fun logInRegisteredUser() {

        if (validateLoginDetails()) {
            showProgressDialog(resources.getString(R.string.please_wait))

            val et_email=findViewById<TextView>(R.id.et_email)
            val et_password=findViewById<TextView>(R.id.et_password)
            val email = et_email.text.toString().trim { it <= ' ' }
            val password =et_password.text.toString().trim { it <= ' ' }

            // Log-In using FirebaseAuth
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    hideProgressDialog()


                    if (task.isSuccessful) {
                        Toast.makeText(
                            this@LoginActivity,
                            resources.getString(R.string.login_success),
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        showErrorSnackBar(task.exception!!.message.toString(), true)
                    }
                }
        }
    }
}