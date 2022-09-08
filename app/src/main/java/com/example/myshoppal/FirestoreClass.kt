package com.example.myshoppal

import android.app.Activity
import android.net.Uri
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.myshoppal.ui.activities.*
import com.example.myshoppal.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.myshoppal.models.Item

class FirestoreClass:BaseActivity() {

    fun getCurrentUserID(): String {
        // An Instance of currentUser using FirebaseAuth
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }

        return currentUserID
    }

    // Access a Cloud Firestore instance.
    private val mFireStore = FirebaseFirestore.getInstance()
    fun uploadImageToCloudStorage(activity: Activity, imageFileURI: Uri?, imageType: String) {

        //getting the storage reference
        val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
            imageType + System.currentTimeMillis() + "."
                    + Constants.getFileExtension(
                activity,
                imageFileURI
            )
        )

        //adding the file to reference
        sRef.putFile(imageFileURI!!)
            .addOnSuccessListener { taskSnapshot ->
                // The image upload is success
                Log.e(
                    "Firebase Image URL",
                    taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
                )

                // Get the downloadable url from the task snapshot
                taskSnapshot.metadata!!.reference!!.downloadUrl
                    .addOnSuccessListener { uri ->
                        Log.e("Downloadable Image URL", uri.toString())

                        // Here call a function of base activity for transferring the result to it.
                        when (activity) {
                            is AddProductActivity -> {
                                activity.imageUploadSuccess(uri.toString())
                            }
                        }
                    }
            }
            .addOnFailureListener { exception ->

                // Hide the progress dialog if there is any error. And print the error in log.
                when (activity) {

                    is AddProductActivity -> {
                        activity.hideProgressDialog()
                    }
                }

                Log.e(
                    activity.javaClass.simpleName,
                    exception.message,
                    exception
                )
            }
    }

    fun uploadItemDetails(activity: AddProductActivity, productInfo: Item) {

        mFireStore.collection(Constants.PRODUCTS)
            .document()
            .set(productInfo, SetOptions.merge())
            .addOnSuccessListener {

                activity.productUploadSuccess()
            }
            .addOnFailureListener { e ->

                activity.hideProgressDialog()

                Log.e(
                    activity.javaClass.simpleName,
                    "Error while uploading the product details.",
                    e
                )
            }
    }

    fun getProductsList(fragment: Fragment) {
        mFireStore.collection(Constants.PRODUCTS)
            .get()
            .addOnSuccessListener { document ->


                Log.e("Products List", document.documents.toString())

                val productsList: ArrayList<Item> = ArrayList()

                for (i in document.documents) {

                    val product = i.toObject(Item::class.java)
                    product!!.item_id = i.id

                    productsList.add(product)
                }

                    }

            .addOnFailureListener { e ->
                when (fragment) {
                    is ProductsFragment -> {
                        fragment.hideProgressDialog()
                    }
                }
                Log.e("Get Product List", "Error while getting product list.", e)
            }


            }


}
