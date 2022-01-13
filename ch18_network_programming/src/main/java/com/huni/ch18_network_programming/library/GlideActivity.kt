package com.huni.ch18_network_programming.library

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.huni.ch18_network_programming.R
import com.huni.ch18_network_programming.databinding.ActivityGlideBinding

class GlideActivity : AppCompatActivity() {
    companion object {
        val TAG = GlideActivity::class.java.simpleName
    }

    private lateinit var binding : ActivityGlideBinding

    private val getImageContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            Glide.with(this).load(result.data?.data).into(binding.ivResult)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGlideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //기본 사용법
        Glide.with(this)
            .load(R.drawable.ic_launcher_background)
            .into(binding.ivResult)

        binding.btGallery.setOnClickListener {
            //gallery app........................
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            getImageContent.launch(intent)
        }

        //Glide를 사용하면 이미지크기줄이거나할떄 OOM을 크게 신경안써도된다.
        //override 사용
//        Glide.with(this)
//            .load(R.drawable.ic_launcher_background)
//            .override(200, 200)
//            .into(binding.ivResult)

        //로딩 및 오류 이미지 출력
        //이미지로딩되기전 -> placeholder, error인 경우 error
//        Glide.with(this)
//            .load(R.drawable.ic_launcher_background)
//            .override(200, 200)
//            .placeholder(R.drawable.ic_launcher_foreground)
//            .error(R.drawable.ic_launcher_background)
//            .into(binding.ivResult)

        //이미지 데이터 사용 -> 불러온 데이터를 커스텀하여 사용
        //Bitmap으로 받을경우는 asBitmap이후에 제너릭을 Bitmap으로 변경.
//        Glide.with(this)
//            .load(R.drawable.ic_launcher_background)
//            .into(object : CustomTarget<Drawable>() {
//                override fun onResourceReady(
//                    resource: Drawable,
//                    transition: Transition<in Drawable>?
//                ) {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onLoadCleared(placeholder: Drawable?) {
//                    TODO("Not yet implemented")
//                }
//            })

    }
}