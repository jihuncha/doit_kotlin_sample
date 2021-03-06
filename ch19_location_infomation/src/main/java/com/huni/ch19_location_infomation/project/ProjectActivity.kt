package com.huni.ch19_location_infomation.project

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnSuccessListener
import com.huni.ch19_location_infomation.R
import com.huni.ch19_location_infomation.databinding.ActivityProjectBinding

class ProjectActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
    OnMapReadyCallback {
    companion object {
        val TAG : String = ProjectActivity::class.java.simpleName
    }

    private lateinit var binding : ActivityProjectBinding

    lateinit var providerClient: FusedLocationProviderClient
    lateinit var apiClient: GoogleApiClient
    var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityProjectBinding.inflate(layoutInflater)
//        setContentView(binding.root)
        setContentView(R.layout.activity_project)

        (supportFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment?)!!.getMapAsync(this)

        providerClient = LocationServices.getFusedLocationProviderClient(this)

        apiClient = GoogleApiClient.Builder(this)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build()

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !== PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_NETWORK_STATE) !== PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_NETWORK_STATE),
                100
            )
        }else {
            //?????? ????????? ??????
            apiClient.connect()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==100 &&
            grantResults[0]===PackageManager.PERMISSION_GRANTED &&
            grantResults[1]===PackageManager.PERMISSION_GRANTED &&
            grantResults[2]===PackageManager.PERMISSION_GRANTED){
            apiClient.connect()
        }
    }

    private fun moveMap(latitude: Double, longitude: Double){
        val latLng = LatLng(latitude, longitude)
        val position: CameraPosition = CameraPosition.Builder()
            .target(latLng)
            .zoom(16f)
            .build()
        //?????? ????????? ??????
        googleMap!!.moveCamera(CameraUpdateFactory.newCameraPosition(position))

        //?????? ??????
        val markerOptions = MarkerOptions()
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        markerOptions.position(latLng)
        markerOptions.title("MyLocation")

        //?????? ?????????
        googleMap?.addMarker(markerOptions)
    }

    //?????? ???????????? ??????????????? ????????????....
    override fun onConnected(p0: Bundle?) {
        if (ContextCompat.checkSelfPermission(this@ProjectActivity,
                Manifest.permission.ACCESS_FINE_LOCATION) === PackageManager.PERMISSION_GRANTED) {
            providerClient.getLastLocation().addOnSuccessListener(
                this@ProjectActivity,
                object : OnSuccessListener<Location> {
                    override fun onSuccess(location: Location?) {
                        Log.d(TAG, "onSuccess")
                        location?.let {
                            Log.d(TAG, "location?.let")
                            val latitude = location.latitude
                            val longitude = location.longitude
                            //?????? ????????? ??????..
                            moveMap(latitude, longitude)
                        }
                    }
                })
            apiClient.disconnect()
        }
    }

    override fun onConnectionSuspended(p0: Int) {
        //???????????? ???????????? ???????????? ?????? ????????? ????????? ??????..
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        //????????? ?????? ???????????? ?????? ??????
    }

    //?????? ????????? ??????????????? ????????? ??????..
    override fun onMapReady(p0: GoogleMap) {
        Log.d(TAG, "onMapReady")
        googleMap=p0
    }

}