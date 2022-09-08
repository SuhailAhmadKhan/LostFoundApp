package com.example.myshoppal.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myshoppal.utils.ItemListAdapter
import com.google.firebase.firestore.*
import com.myshoppal.models.Item
import com.example.myshoppal.R


class LostActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<Item>
    private lateinit var myAdapter: ItemListAdapter
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lost)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )


        val btn_lost_item = findViewById<Button>(R.id.btn_lost_item)
        btn_lost_item.setOnClickListener {
            val intent = Intent(this@LostActivity, AddProductActivity::class.java)
            startActivity(intent)
            finish()
        }

        // recyclerAnimals.adapter = mAdapter
        setupActionBar()

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        userArrayList = arrayListOf()
        myAdapter = ItemListAdapter(userArrayList)
        recyclerView.adapter = myAdapter
        EventChangeListener()

    }

    private fun EventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("products").addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null) {
                    Log.e("Firestore Error", error.message.toString())
                    return
                }
                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        userArrayList.add(dc.document.toObject(Item::class.java))

                    }

                }
                myAdapter.notifyDataSetChanged()
            }

        })
    }


    private fun setupActionBar() {
        val toolBar: androidx.appcompat.widget.Toolbar
        toolBar = findViewById(R.id.toolbar_lost_activity)

        setSupportActionBar(toolBar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        }
        toolBar.setNavigationOnClickListener {
            val intent = Intent(this@LostActivity, MainActivity::class.java)
            startActivity(intent)
            finish()


        }
    }
}