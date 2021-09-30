package com.huni.ch11_jetpack_library

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huni.ch11_jetpack_library.databinding.ActivityRecyclerViewBinding
import com.huni.ch11_jetpack_library.databinding.RecyclerviewItemBinding


class RecyclerViewActivity : AppCompatActivity() {
    val TAG:String = RecyclerViewActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_recycler_view)

        val binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val datas = mutableListOf<String>()
        for (i in 1..10) {
            datas.add("Item $i")
        }

        //        binding.recyclerview.layoutManager = LinearLayoutManager(this)

        //가로화면 적용
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
//        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
//        binding.recyclerview.layoutManager = layoutManager

        //Grid 세로
//        val layoutManager = GridLayoutManager(this, 2)
        //Grid 가로
        //reverseLayout - true : 오른쪽/아래부터 시작
//        val layoutManager = GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false)
        
        //높이가 불규칙한 그리드
        //StaggeredGridLayoutManager 사용

        binding.recyclerview.layoutManager = layoutManager

        binding.recyclerview.adapter = MyAdapter(datas)

//        binding.recyclerview.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        
        //Custom 데코레이션 사용
        binding.recyclerview.addItemDecoration(MyDecoration(this))
    }
}

//viewBinding 사용
class MyViewHolder(val binding: RecyclerviewItemBinding): RecyclerView.ViewHolder(binding.root)

class MyAdapter(val datas: MutableList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val TAG:String = MyAdapter::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            RecyclerviewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder - ${position}")

        val binding = (holder as MyViewHolder).binding

        binding.itemData.text = datas[position]

        binding.itemRoot.setOnClickListener {
            Log.d(TAG, "item root click - $position")
        }
    }

    override fun getItemCount(): Int {
        return datas.size
    }
}

//아이템 Decoration
//onDraw - 항목이 배치되기전 호출
//onDrawOver - 항목이 배치된 후 호출
//getItemOffsets - 개별항목을 꾸밀떄
class MyDecoration(val context: Context): RecyclerView.ItemDecoration() {
    val TAG:String = MyDecoration::class.java.simpleName

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        c.drawBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.kbo), 0f, 0f, null)
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        //뷰 크기 계산
        val width = parent.width
        val height = parent.height

        //이미지 크기 계산
        val dr :Drawable? = ResourcesCompat.getDrawable(context.resources, R.drawable.staidum, null)

        val drWidth = dr?.intrinsicWidth
        val drheight = dr?.intrinsicHeight

        //이미지가 그려질 위치 계싼
        val left = (width / 2) - drWidth?.div(2) as Int
        val top = (height / 2) - drheight?.div(2) as Int

        Log.d(TAG, "left - $left , top - $top")

        c.drawBitmap(
            BitmapFactory.decodeResource(context.resources, R.drawable.staidum),
            left.toFloat(),
            top.toFloat(),
            null
        )
        
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val index = parent.getChildAdapterPosition(view) + 1
        if (index % 3 == 0)
            outRect.set(10,10,10,60) //left, top, right, bottom
        else
            outRect.set(10,10,10,0) //left, top, right, bottom
        view.setBackgroundColor(Color.LTGRAY)
        ViewCompat.setElevation(view, 20.0f)
    }
}

