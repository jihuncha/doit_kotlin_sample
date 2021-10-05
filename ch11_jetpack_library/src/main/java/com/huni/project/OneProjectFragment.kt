package com.huni.project

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huni.ch11_jetpack_library.R
import com.huni.ch11_jetpack_library.databinding.FragmentProjectOneBinding
import com.huni.ch11_jetpack_library.databinding.ItemRecyclerviewBinding

//항목 구성자
class ProjectViewHolder(val binding: ItemRecyclerviewBinding): RecyclerView.ViewHolder(binding.root)
//항목 구성자 어댑터
class ProjectAdapter(val datas: MutableList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val TAG:String = ProjectAdapter::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProjectViewHolder(
            ItemRecyclerviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder - ${position}")

        val binding = (holder as ProjectViewHolder).binding

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
class ProjectDecoration(val context: Context): RecyclerView.ItemDecoration() {
    val TAG:String = ProjectDecoration::class.java.simpleName

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
        val dr : Drawable? = ResourcesCompat.getDrawable(context.resources, R.drawable.kbo, null)

        val drWidth = dr?.intrinsicWidth
        val drheight = dr?.intrinsicHeight

        //이미지가 그려질 위치 계산
        val left = (width / 2) - drWidth?.div(2) as Int
        val top = (height / 2) - drheight?.div(2) as Int

        Log.d(TAG, "left - $left , top - $top")

        c.drawBitmap(
            BitmapFactory.decodeResource(context.resources, R.drawable.kbo),
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
        view.setBackgroundColor(Color.parseColor("#28A0FF"))
        ViewCompat.setElevation(view, 20.0f)
    }
}


class OneProjectFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProjectOneBinding.inflate(inflater, container, false)

        val datas = mutableListOf<String>()

        for (i in 1..9) {
            datas.add("items - $i")
        }

        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerview.layoutManager = layoutManager

        val adapter: ProjectAdapter = ProjectAdapter(datas)
        binding.recyclerview.adapter = adapter
        binding.recyclerview.addItemDecoration(ProjectDecoration(activity as Context))

        return binding.root
    }
}