package com.huni.ch18_network_programming.library

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.*
import com.huni.ch18_network_programming.databinding.ActivityVolleyBinding
import org.json.JSONArray
import org.json.JSONObject

class VolleyActivity : AppCompatActivity() {
    companion object {
        val TAG:String = VolleyActivity::class.java.simpleName
    }

    private val url:String = "http://www.google.com"
    private lateinit var binding: ActivityVolleyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVolleyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.btStringTest.setOnClickListener {
            //서버연동 성공/실패
            //Get방식
            val stringRequest = StringRequest(
                Request.Method.GET,
                url,
                Response.Listener<String> {
                    //구글 요청하면 html태그가 전부 내려온다.
                    Log.d(TAG, "test1 - $it")
                },
                Response.ErrorListener { error ->
                    Log.d(TAG, "error!! - $error")
                }
            )

            //Post방식
//            val stringRequest = object:StringRequest(
//                Request.Method.POST,
//                url,
//                Response.Listener<String> {
//                    Log.d(TAG, "test1")
//                },
//                Response.ErrorListener { error ->
//                    Log.d(TAG, "error!! - $error")
//                }) {
//                override fun getParams(): MutableMap<String, String> {
//                    return mutableMapOf<String, String>("one" to "hello", "two" to "world")
//                }
//            }

            //서버에 요청하기
            val queue = Volley.newRequestQueue(this)
            queue.add(stringRequest)
        }

        binding.btImageTest.setOnClickListener {
            //ImageRequest 사용
            //이미지 크기를 조절 가능 (maxwidth,maxheight) - 0으로 설정하면 서버가 전달하는 이미지를 그대로 받는다.

/*            val imageRequest = ImageRequest (
                url,
                Response.Listener { response -> binding.ivImage.setImageBitmap(response) },
                0,
                0,
                ImageView.ScaleType.CENTER_CROP,
                null,
                Response.ErrorListener { error ->
                    Log.e(TAG, "Error!!! - $error")
                })

            val queue = Volley.newRequestQueue(this)
            queue.add(imageRequest)*/

            //xml에 화면출력용 이미지 요청 가능 -> com.android.volley.toolbox.NetworkImageView
            //setImageUrl 사용하면 자동 출력까지 된다.
            //setImageUrl로 요청해보기

            val queue = Volley.newRequestQueue(this)
            val imgMap = HashMap<String, Bitmap>()
            val imageLoader = ImageLoader(queue, object : ImageLoader.ImageCache {
                override fun getBitmap(data: String): Bitmap? {
                    return imgMap[data]
                }

                override fun putBitmap(data: String, bitmap: Bitmap) {
                    imgMap[data] = bitmap
                }
            })
            binding.ivNetworkImage.setImageUrl(url, imageLoader)
        }

        binding.btJsonTest.setOnClickListener {
            //jsonObject
            val jsonRequest = JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                Response.Listener<JSONObject> { response ->
                    //TODO..
                },
                Response.ErrorListener { error ->
                    Log.e(TAG, "btJsonTest/ERROR!! - $error")
                })

            //서버에 요청하기
            val queue = Volley.newRequestQueue(this)
            queue.add(jsonRequest)

            //jsonArray
            val jsonArrayRequest = JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                Response.Listener<JSONArray> { response ->
                    //TODO...(response.length for문으로 object가져온다..)
                },
                Response.ErrorListener { error ->
                    Log.e(TAG, "btJsonTest/ERROR!! - $error")
                })

//            val queue = Volley.newRequestQueue(this)
//            queue.add(jsonArrayRequest)
        }
    }
}