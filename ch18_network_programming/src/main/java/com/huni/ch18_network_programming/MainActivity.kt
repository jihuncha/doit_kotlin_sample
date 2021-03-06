package com.huni.ch18_network_programming

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.huni.ch18_network_programming.databinding.ActivityMainBinding
import com.huni.ch18_network_programming.library.GlideActivity
import com.huni.ch18_network_programming.library.RetrofitActivity
import com.huni.ch18_network_programming.library.VolleyActivity
import com.huni.ch18_network_programming.project.ProjectActivity

class MainActivity : AppCompatActivity() {
    companion object {
        val TAG : String = MainActivity::class.java.simpleName
    }

    private lateinit var binding : ActivityMainBinding
    private val url:String = "http://www.google.com"

    //READ_PHONE_NUMBERS
    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                val manager: TelephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                val countryIso = manager.networkCountryIso
                val operatorName = manager.networkOperatorName
//                val phoneNumber = manager.line1Number

                Log.d(TAG, "countryIso - $countryIso , operatorName - $operatorName")

            } else if (ActivityCompat.shouldShowRequestPermissionRationale(this,  android.Manifest.permission.READ_CONTACTS)) {
                //이전에 거부한 경우
                Toast.makeText(this, "Permission Necessary!!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Go to Setting!!", Toast.LENGTH_SHORT).show()
            }
        }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //스마트폰 정보 구하기 -> 앱에서 전화기능을 이용할경우
        //PhoneStateListener를 상속받아 그 객체를 TelephonyManager에 등록해야함

        //TODO 추후 통화관련로직 확인 및 구현 필요
        //TODO Deprecated됨.. https://developer.android.com/reference/android/telephony/TelephonyCallback 사용
//        val phoneStateListener = MyPhoneStateListener()

//        phoneStateListener.reg

        //네트워크 제공 체크
        val permission = android.Manifest.permission.READ_PHONE_NUMBERS
        requestPermissionLauncher.launch(permission)

        binding.btMain.setOnClickListener {
            Toast.makeText(this, "network - ${isNetworkAvailable()}", Toast.LENGTH_SHORT).show()
        }

        binding.btVolley.setOnClickListener {
            val intent = Intent(this, VolleyActivity::class.java)
            startActivity(intent)
        }

        binding.btRetrofit.setOnClickListener {
            val intent = Intent(this, RetrofitActivity::class.java)
            startActivity(intent)
        }

        binding.btGlide.setOnClickListener {
            val intent = Intent(this, GlideActivity::class.java)
            startActivity(intent)
        }

        binding.btProject.setOnClickListener {
            val intent = Intent(this, ProjectActivity::class.java)
            startActivity(intent)
        }


    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.d(TAG, "wifi available")
                    true
                }
                else -> false
            }
        } else {
            return connectivityManager.activeNetworkInfo?.isConnected ?: false
        }
    }
}

//@RequiresApi(Build.VERSION_CODES.S)
//class MyPhoneStateListener: TelephonyCallback(), TelephonyCallback.CallStateListener {
//    companion object {
//        val TAG: String = MyPhoneStateListener::class.java.simpleName
//    }
//
//    override fun onCallStateChanged(state: Int) {
//        Log.d(TAG, "state - $state")
//    }
//
//}