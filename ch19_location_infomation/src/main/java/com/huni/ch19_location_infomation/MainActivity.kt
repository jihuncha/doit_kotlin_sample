package com.huni.ch19_location_infomation

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Api
import com.google.android.gms.common.api.GoogleApi
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.huni.ch19_location_infomation.databinding.ActivityMainBinding
import com.huni.ch19_location_infomation.project.ProjectActivity

class MainActivity : AppCompatActivity() {
    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityMainBinding

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //LocationManager 시스템 서비스
        val manager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        //위치 제공자 (location provider 구하기)
        var result = "All Providers : "
//        val providers = manager.allProviders
//        for (provider in providers) {
//            result += "$provider, "
//        }
//
//        Toast.makeText(this, result, Toast.LENGTH_SHORT).show()

        //당장사용할수있는 providers
        val providers = manager.getProviders(true)
        for (provider in providers) {
            result += "$provider, "
        }

        Toast.makeText(this, result, Toast.LENGTH_SHORT).show()

        binding.btGetLocationOnce.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                === PackageManager.PERMISSION_GRANTED
            ) {
                Log.d(TAG, "here??")

                val location: Location? =
                    manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

                //TODO - 위치가 안보임 안움직여서 그런가 location 이 자꾸 null나온다..
                //Network Provider 사용하면나옴!!
                location?.let {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    val accuracy = location.accuracy
                    val time = location.time

                    Toast.makeText(
                        this,
                        "test - $latitude, $longitude, $accuracy, $time",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.btGetLocationAlways.setOnClickListener {
            //위치 계속 가져올 경우 - LocationListener
            val listener:LocationListener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    Log.d(TAG, "onLocationChanged - $location")
                }

                override fun onProviderEnabled(provider: String) {
                    Log.d(TAG, "onProviderEnabled")
                    super.onProviderEnabled(provider)
                }

                override fun onProviderDisabled(provider: String) {
                    Log.d(TAG, "onProviderDisabled")
                    super.onProviderDisabled(provider)
                }
            }

            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10f, listener)
        }


        //fused location api 이용 
        //FusedLocationProviderClient - 위치정보를 얻습니다
        //GoogleApiClient - 위치 제공자 준비 등 다양한 콜백을 제공합니다
        //GoogleApiClient 결정 -> FusedLocationProviderClient로 위치를 가져온다.

        binding.btFusedLocation.setOnClickListener {
            //deprecated...GoogleApiClient.ConnectionCallbacks
            //TODO https://android-developers.googleblog.com/2017/11/moving-past-googleapiclient_21.html

            // Code required for requesting location permissions omitted for brevity.
            //fused 초기화
            val client = LocationServices.getFusedLocationProviderClient(this)

            // Get the last known location. In some rare situations, this can be null.
            client.lastLocation.addOnSuccessListener { location : Location? ->
                location?.let {
                    Log.d(TAG, "btFusedLocation/location - $it")
                }
            }
        }

//        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestEmail()
//            .build()
//
//        mGoogleSignInClient = GoogleSignIn.getClient(this, options)


        binding.btGoogleMap.setOnClickListener {
            val intent = Intent(this, ProjectActivity::class.java)
            startActivity(intent)
        }
    }
}