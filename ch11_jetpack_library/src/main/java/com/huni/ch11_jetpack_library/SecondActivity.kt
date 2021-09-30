package com.huni.ch11_jetpack_library

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.huni.ch11_jetpack_library.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    val TAG : String = SecondActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_second)

        val binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Toolbar
        setSupportActionBar(binding.toolbar)

        //Activity 코드로 up 버튼 정의 -> toolbar로해도 적용된다.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    //up 버튼 정의
    // 1. Manifest 에서
    // 2. 소스 코드에서 - supportActionBar?.setDisplayHomeAsUpEnabled(true)
    override fun onSupportNavigateUp(): Boolean {
        Log.e(TAG, "onSupportNavigateUp - SUCCESS!!!")
        //코드에서 사용하면 이거 호출해줘야한다.!!
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    // 메뉴 구성 1
    // onCreateOptionsMenu - Activity 실행시 호출
    // onPrepareOptionsMenu - Activity 실행시 호출 + 오버플로 메뉴가 나타날 때마다 반복 호출
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//
//        //두번쨰인자 - id값 -> 해당 id값 이용하여 select 처리
//        val menuItem1: MenuItem? = menu?.add(0,0,0, "menu1")
//        val menuItem2: MenuItem? = menu?.add(0,1,0, "menu2")
//
//        return super.onCreateOptionsMenu(menu)
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId) {
//            0 -> {
//                Log.d(TAG, "menu1 clicked!!")
//                true
//            }
//
//            1 -> {
//                Log.d(TAG, "menu2 clicked!!")
//                true
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }

    // 메뉴 구성 2 - xml 사용
    // 액션 뷰
    // -> xml item 에서 actionViewClass = androidx.appcompat.widget.SearchView 사용
/*    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_main, menu)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        val menuItem = menu?.findItem(R.id.menu_search)
        val searchView = menuItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                // 키보드의 검색 버튼 클릭시 이벤트 실행됨
                Log.d(TAG, "result - $query")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //검색어 변경 이벤트
                Log.d(TAG, "change - $newText")
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }*/

    ////////////////////////////////////////////////////
    //Toolbar
    //목적은 Actionbar와 동일
    //Actionbar - Activity 창이 자동으로 출력해줌
    //Toolbar - 개발자가 직접 제어
    //onCreate - setSupportActionBar(binding.toolber) 참고

}