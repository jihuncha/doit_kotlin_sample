package com.huni.ch21_firebase_etc.storage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.huni.ch21_firebase_etc.MyApplication
import com.huni.ch21_firebase_etc.R
import com.huni.ch21_firebase_etc.databinding.ActivityStorageMainBinding
import com.huni.ch21_firebase_etc.model.ItemData
import com.huni.ch21_firebase_etc.recycler.MyAdapter
import com.huni.ch21_firebase_etc.util.myCheckPermission

class StorageMainActivity : AppCompatActivity() {
    companion object {
        val TAG : String = StorageMainActivity::class.java.simpleName
    }

    lateinit var binding : ActivityStorageMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStorageMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myCheckPermission(this)

        binding.addFab.setOnClickListener {
            if(MyApplication.checkAuth()){
                startActivity(Intent(this, StorageAddActivity::class.java))
            }else {
                Toast.makeText(this, "인증을 먼저 진행해 주세요", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onStart() {
        super.onStart()
        if(!MyApplication.checkAuth()){
            binding.logoutTextView.visibility= View.VISIBLE
            binding.mainRecyclerView.visibility=View.GONE
        }else {
            binding.logoutTextView.visibility= View.GONE
            binding.mainRecyclerView.visibility=View.VISIBLE
            makeRecyclerView()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        startActivity(Intent(this, StorageAuthActivity::class.java))
        return super.onOptionsItemSelected(item)
    }

    private fun makeRecyclerView(){
        MyApplication.db.collection("news")
            .get()
            .addOnSuccessListener { result ->
                val itemList = mutableListOf<ItemData>()
                for (document in result) {
                    val item = document.toObject(ItemData::class.java)
                    item.docId=document.id
                    itemList.add(item)
                }

                binding.mainRecyclerView.layoutManager= LinearLayoutManager(this)
                binding.mainRecyclerView.adapter= MyAdapter(this, itemList)
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
                Toast.makeText(this, "서버로부터 데이터 획득에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
    }
}