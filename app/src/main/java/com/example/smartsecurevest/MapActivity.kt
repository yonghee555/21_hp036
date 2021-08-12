package com.example.smartsecurevest

import android.Manifest
import android.annotation.SuppressLint
import android.app.FragmentManager
import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*



class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    val permission = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION)

    val PERM_FlAG = 99
    private lateinit var mMap: GoogleMap

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationCallback:LocationCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

    if(isPermitted()){
        startProcess()
    } else
        ActivityCompat.requestPermissions(this,permission,PERM_FlAG)
    }

    fun isPermitted() : Boolean{
        for(perm in permission){
            if(ContextCompat.checkSelfPermission(this,perm) != PERMISSION_GRANTED){
                return false
            }
        }
        return true
    }

    fun startProcess(){
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        fusedLocationProviderClient =LocationServices.getFusedLocationProviderClient(this)
        setUpdateLocationListener()
    }

    //내 위치 가져오기
    @SuppressLint("MissingPermission")
    fun setUpdateLocationListener(){
        val locationRequest =LocationRequest.create()
        locationRequest.run { priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        interval = 1000
        }
        locationCallback = object : LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult?.let {
                    for((i,location) in it.locations.withIndex()){
                        Log.d("로케이션","$i ${location.latitude},${location.longitude}")
                        setLastLocation(location)
                    }
                }
            }
        }
        //로케이션 요청 함수 호출
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,Looper.myLooper())
    }

    fun setLastLocation(location: Location){
        val myLocation = LatLng(location.latitude,location.longitude)
        val marker = MarkerOptions().position(myLocation).title("My location")
        val cameraOption = CameraPosition.Builder()
                .target(myLocation)
                .zoom(15.0f)
                .build()
        val camera = CameraUpdateFactory.newCameraPosition(cameraOption)

        mMap.clear()
        mMap.addMarker(marker)
        mMap.moveCamera(camera)
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray) {
        when(requestCode){
            PERM_FlAG->{
                var check = true
                for(grant in grantResults){
                    if(grant != PERMISSION_GRANTED){
                        check = false
                        break
                    }
                }
                if(check){
                    startProcess()
                }else{
                    Toast.makeText(this,"권한을 승인하지않아 앱을 사용할 수 없습니다.",Toast.LENGTH_LONG)
                    finish()
                }
            }
        }
    }


}