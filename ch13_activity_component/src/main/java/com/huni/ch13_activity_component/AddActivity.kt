package com.huni.ch13_activity_component

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huni.ch13_activity_component.databinding.ActivityAddBinding
import com.huni.ch13_activity_component.databinding.ItemRecyclerviewBinding

// todoList 
// startActivityForResult가 변경됬다ㅋㅋㅋㅋㅋ
class AddActivity : AppCompatActivity() {
    val TAG:String = AddActivity::class.java.simpleName

    private lateinit var binding: ActivityAddBinding

    private lateinit var getResult: ActivityResultLauncher<Intent>
    private lateinit var adapter: MyAdapter

    private var datas: MutableList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_add)

        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //TODO 이문법 잘 이해가 안감.
        datas = savedInstanceState?.let {
            it.getStringArrayList("datas")?.toMutableList()
        } ?: let {
            //엘비스연산자로 let??
            mutableListOf<String>()
        }

        initView()
    }

    //화면전환시에도 사라지지않게
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState")
        outState.putStringArrayList("datas", ArrayList(datas))
    }

    private fun initView() {
        Log.d(TAG, "initView")
        //    https://developer88.tistory.com/351
        getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                Log.d(TAG, "resultCode - OK!!")
                it.data!!.getStringExtra("result")?.let {
                    Log.d(TAG, "check - ${it}")
                    //지정된 값이 null 이 아닌 경우에 코드를 실행해야 하는 경우. - let 사용
                    datas?.add(it)
                    adapter.notifyDataSetChanged()
                }
            }
        }

        binding.mainFab.setOnClickListener {
            val intent : Intent = Intent(this@AddActivity, AddDetailActivity::class.java)
//            startActivityForResult()
            getResult.launch(intent)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.mainRecyclerView.layoutManager = layoutManager
        adapter = MyAdapter(datas)
        binding.mainRecyclerView.adapter=adapter
        binding.mainRecyclerView.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )
    }
}

//class MyAdapter

class MyViewHolder(val binding: ItemRecyclerviewBinding): RecyclerView.ViewHolder(binding.root)

class MyAdapter(val datas: MutableList<String>?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding=(holder as MyViewHolder).binding
        binding.itemData.text= datas!![position]
    }

    override fun getItemCount(): Int {
        return datas?.size?: 0
    }

}