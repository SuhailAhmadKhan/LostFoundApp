package com.example.myshoppal.ui.activities


import android.os.Bundle
import android.view.*
import com.example.myshoppal.FirestoreClass

import com.example.myshoppal.ui.activities.Fragments.BaseFragment
import com.example.myshoppal.R


class ProductsFragment : BaseFragment() {

    private lateinit var mRootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private fun getItemListFromFireStore() {
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getProductsList(this@ProductsFragment)
    }

    override fun onResume() {
        super.onResume()

        getItemListFromFireStore()
    }


    }
