package com.example.myshoppal.ui.activities

import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.*
import com.example.myshoppal.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore


@Suppress("DEPRECATION")
 class RegisterActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val login: TextView
        login = findViewById(R.id.tv_login)
        login.setOnClickListener {
            onBackPressed()
        }
        val toolBar: androidx.appcompat.widget.Toolbar
        toolBar = findViewById(R.id.toolbar_register_activity)

        setupActionBar()

        val btn_register=findViewById<Button>(R.id.btn_register)
        btn_register.setOnClickListener {

            registerUser()
        }

        // START
        val tv_login=findViewById<TextView>(R.id.tv_login)
        tv_login.setOnClickListener{
            onBackPressed()
        }
    }
    private fun setupActionBar() {
        val toolBar: androidx.appcompat.widget.Toolbar
        toolBar = findViewById(R.id.toolbar_register_activity)

        setSupportActionBar(toolBar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        }
        toolBar.setNavigationOnClickListener { onBackPressed() }
    }
    private fun validateRegisterDetails(): Boolean {
        val et_first_name:EditText
        et_first_name=findViewById(R.id.et_first_name)
        val et_last_name=findViewById<TextView>(R.id.et_last_name)
        val et_email=findViewById<TextView>(R.id.et_email)
        val et_password=findViewById<EditText>(R.id.et_password)
        val et_confirm_password=findViewById<EditText>(R.id.et_confirm_password)
        val cb_terms_and_condition=findViewById<CheckBox>(R.id.cb_terms_and_condition)
        return when {

            TextUtils.isEmpty(et_first_name.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_first_name), true)
                false
            }

            TextUtils.isEmpty(et_last_name.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_last_name), true)
                false
            }

            TextUtils.isEmpty(et_email.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }

            TextUtils.isEmpty(et_password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }

            TextUtils.isEmpty(et_confirm_password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_enter_confirm_password),
                    true
                )
                false
            }

            et_password.text.toString().trim { it <= ' ' } != et_confirm_password.text.toString()
                .trim { it <= ' ' } -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_password_and_confirm_password_mismatch),
                    true
                )
                false
            }
            !cb_terms_and_condition.isChecked -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_agree_terms_and_condition),
                    true
                )
                false
            }
            else -> {
                true
            }
        }
    }
    private fun registerUser() {
        val et_email=findViewById<EditText>(R.id.et_email)
        val et_password=findViewById<EditText>(R.id.et_password)
        val et_first_name=findViewById<EditText>(R.id.et_first_name)
        val et_last_name=findViewById<EditText>(R.id.et_last_name)

        if (validateRegisterDetails()) {
            showProgressDialog(resources.getString(R.string.please_wait))

            val email: String = et_email.text.toString().trim { it <= ' ' }
            val password: String = et_password.text.toString().trim { it <= ' ' }
            val firstName: String = et_first_name.text.toString().trim { it <= ' ' }
            val lastName:String = et_last_name.text.toString().trim { it <= ' ' }

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> { task ->

                        // START

                        hideProgressDialog()
                        // END

                        // If the registration is successfully done
                        if (task.isSuccessful) {

                            // Firebase registered user
                            val firebaseUser: FirebaseUser = task.result!!.user!!

                            Toast.makeText(
                                this@RegisterActivity,
                                resources.getString(R.string.register_success),
                                Toast.LENGTH_SHORT
                            ).show()
                          saveFireStore(firstName,lastName)


//                            FirebaseAuth.getInstance().signOut()
//                            finish()



                        } else {
                            hideProgressDialog()
                            showErrorSnackBar(task.exception!!.message.toString(), true)
                        }
                    })
        }
    }
    fun saveFireStore(firstname:String, lastname:String){
        val db=FirebaseFirestore.getInstance()
        val user: MutableMap<String,Any> = HashMap()
        user["firstName"]=firstname
        user["lastName"]=lastname
        db.collection("User")
            .add(user)
            .addOnSuccessListener {
                Toast.makeText(this@RegisterActivity,"record added Succesfully",Toast.LENGTH_SHORT).show() }
            .addOnFailureListener{
                Toast.makeText(this@RegisterActivity,"record Failed to add",Toast.LENGTH_SHORT).show() }
    }


    // END
}





