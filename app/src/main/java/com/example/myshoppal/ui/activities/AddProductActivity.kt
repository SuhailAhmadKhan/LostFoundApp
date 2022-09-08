package com.example.myshoppal.ui.activities

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myshoppal.FirestoreClass
import com.example.myshoppal.utils.Constants
import com.example.myshoppal.utils.GlideLoader
import com.example.myshoppal.utils.ItemListAdapter
import com.google.firebase.database.*
import com.myshoppal.models.Item
import kotlinx.android.synthetic.main.fragment_products.*
import java.io.IOException
import com.example.myshoppal.R


class AddProductActivity : BaseActivity(), View.OnClickListener {
    private var mSelectedImageFileUri: Uri? = null
    lateinit var mDataBase: DatabaseReference
    private lateinit var itemList:ArrayList<Item>
    private lateinit var mAdapter: ItemListAdapter

    // A global variable for uploaded product image URL.
    private var mProductImageURL: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN

        )
        setupActionBar()
                val iv_add_update_product=findViewById<ImageView>(R.id.iv_add_update_product)
        iv_add_update_product.setOnClickListener(this)
        val btn_submit:Button
        btn_submit=findViewById(R.id.btn_submit_lost)
        btn_submit.setOnClickListener(this)
    }
    private fun setupActionBar() {
        val toolBar: androidx.appcompat.widget.Toolbar
        toolBar = findViewById(R.id.toolbar_add_product_activity)

        setSupportActionBar(toolBar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        }
        toolBar.setNavigationOnClickListener {
            val intent = Intent(this@AddProductActivity, LostActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onClick(view: View?) {

        if (view != null) {
            when (view.id) {

                R.id.iv_add_update_product -> {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                        == PackageManager.PERMISSION_GRANTED
                    ) {
                        Constants.showImageChooser(this@AddProductActivity)
                    } else {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            Constants.READ_STORAGE_PERMISSION_CODE
                        )
                    }
                }
                R.id.btn_submit_lost -> {
                    if (validateProductDetails()) {
                        uploadProductImage()
                    }

                }
            }
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.READ_STORAGE_PERMISSION_CODE) {
            //If permission is granted
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Constants.showImageChooser(this@AddProductActivity)
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(
                    this,
                    resources.getString(R.string.read_storage_permission_denied),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK
            && requestCode == Constants.PICK_IMAGE_REQUEST_CODE
            && data!!.data != null
        ) {
            val iv_add_update_product=findViewById<ImageView>(R.id.iv_add_update_product)
            iv_add_update_product.setImageDrawable(
                ContextCompat.getDrawable(
                    this@AddProductActivity,
                    R.drawable.ic_vector_edit
                )
            )
            mSelectedImageFileUri = data.data!!
            val iv_product_image=findViewById<ImageView>(R.id.iv_product_image)
            try {
                GlideLoader(this@AddProductActivity).loadProductPicture(
                    mSelectedImageFileUri!!,
                    iv_product_image
                )
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun uploadProductImage() {

        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().uploadImageToCloudStorage(
            this@AddProductActivity,
            mSelectedImageFileUri,
            Constants.PRODUCT_IMAGE
        )
    }
    fun imageUploadSuccess(imageURL: String) {
        mProductImageURL = imageURL

        uploadItemDetails()
    }
    private fun uploadItemDetails() {

        val username =
            this.getSharedPreferences(Constants.MYSHOPPAL_PREFERENCES, Context.MODE_PRIVATE)
                .getString(Constants.LOGGED_IN_USERNAME, "")!!

        // Here we get the text from editText and trim the space
        val et_description=findViewById<EditText>(R.id.et_description)
        val et_location=findViewById<EditText>(R.id.et_possible_location)
        val item = Item(
            FirestoreClass().getCurrentUserID(),
            username,
            et_description.text.toString().trim { it <= ' ' },
            et_location.text.toString().trim { it <= ' ' },

            mProductImageURL
        )

        FirestoreClass().uploadItemDetails(this@AddProductActivity, item)
    }
    private fun validateProductDetails(): Boolean {
        val et_description=findViewById<EditText>(R.id.et_description)
        val et_location=findViewById<EditText>(R.id.et_possible_location)
        return when {

            mSelectedImageFileUri == null -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_select_product_image), true)
                false
            }

            TextUtils.isEmpty(et_location.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_product_title), true)
                false
            }


            TextUtils.isEmpty(et_description.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_enter_description),
                    true
                )
                false
            }
            else -> {
                true
            }
        }
    }
    fun productUploadSuccess() {
        hideProgressDialog()

        Toast.makeText(
            this@AddProductActivity,
            "Your item is uploaded successfully.",
            Toast.LENGTH_SHORT
        ).show()
        val intent = Intent(this@AddProductActivity, MainActivity::class.java)
        startActivity(intent)

    }

}