package com.huni.ch17_storage.project

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ch17_database.DBHelper
import com.huni.ch17_storage.R
import com.huni.ch17_storage.databinding.ActivityProjectBinding


class ProjectActivity : AppCompatActivity() {
    companion object {
        val TAG: String = ProjectActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityProjectBinding
    var datas: MutableList<String>? = null
    lateinit var adapter: MyAdapter

    //ActivityResult - Gallery
    private val getActivityResultMainTab =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activtyResult ->
            if (activtyResult.resultCode == Activity.RESULT_OK) {
                activtyResult.data!!.getStringExtra("result")?.let {
                    datas?.add(it)
                    adapter.notifyDataSetChanged()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainFab.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            getActivityResultMainTab.launch(intent)
        }

        datas = mutableListOf<String>()

        //add......................
        //DB에서 데이터를 select 이후 가져온다.
        val db = DBHelper(this).readableDatabase
        val cursor = db.rawQuery("select * from TODO_TB", null)
        cursor.run {
            while (moveToNext()) {
                datas?.add(cursor.getString(1))
            }
        }
        db.close()

        val layoutManager = LinearLayoutManager(this)
        binding.mainRecyclerView.layoutManager = layoutManager
        adapter = MyAdapter(datas)
        binding.mainRecyclerView.adapter = adapter
        binding.mainRecyclerView.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId === R.id.menu_main_setting) {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}