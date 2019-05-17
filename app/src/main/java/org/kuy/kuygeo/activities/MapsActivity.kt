package org.kuy.kuygeo.activities

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import org.kuy.kuygeo.domain.MY_PERMISSION_CODE
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import android.graphics.Bitmap
import android.graphics.Canvas
import android.support.v7.app.AlertDialog
import android.widget.EditText
import android.widget.TextView
import com.google.android.gms.maps.model.BitmapDescriptor
import kotlinx.android.synthetic.main.popup_geoalert.*
import org.kuy.kuygeo.R
import org.kuy.kuygeo.domain.GeoAlert
import org.kuy.kuygeo.service.GeoAlertService


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private var latitude: Double = 0.toDouble()
    private var longitude: Double = 0.toDouble()
    private lateinit var lastLocation: Location
    private var currentPosMarker: Marker? = null

    private val geoAlertService: GeoAlertService = GeoAlertService(this)
    private var geoAlerts : List<GeoAlert> = emptyList()
    private var markers : Array<Marker> = emptyArray()


    //location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest

    lateinit var locationCallback: LocationCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        initializeComponents()
    }

    private fun initializeComponents() {
        //geoAlertService.deleteAll()
        handlePermission()
        handleLocation()
    }

    private fun handleLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission(ACCESS_FINE_LOCATION) && checkPermission(ACCESS_COARSE_LOCATION)) {
                buildLocationRequest()
                buildLocationCallback()
                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
            }
        } else {
            buildLocationRequest()
            buildLocationCallback()
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
        }

    }

    private fun handlePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!checkPermission(ACCESS_FINE_LOCATION)) {
                requestPermission(ACCESS_FINE_LOCATION)
            }
            if (!checkPermission(ACCESS_COARSE_LOCATION)) {
                requestPermission(ACCESS_COARSE_LOCATION)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (checkPermission(ACCESS_FINE_LOCATION) && checkPermission(ACCESS_COARSE_LOCATION)) {
                        buildLocationRequest()
                        buildLocationCallback()
                        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
                        fusedLocationProviderClient.requestLocationUpdates(
                            locationRequest,
                            locationCallback,
                            Looper.myLooper()
                        )
                        mMap.isMyLocationEnabled = true
                    }
                } else {
                    Toast.makeText(this, "Permiso denegado", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onStop() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        super.onStop()
    }

    private fun checkPermission(permission: String): Boolean {
        return isGrantedPermission(permission)
    }

    private fun requestPermission(permission: String) {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                permission
            ), MY_PERMISSION_CODE
        )
    }

    private fun isGrantedPermission(permission: String) =
        ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED

    private fun buildLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 3000
        locationRequest.smallestDisplacement = 10f
    }

    private fun buildLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                val lastLocationIndex = locationResult!!.locations.size - 1
                lastLocation = locationResult.locations[lastLocationIndex]

                if (currentPosMarker != null) {
                    currentPosMarker!!.remove()
                }
                latitude = lastLocation.latitude
                longitude = lastLocation.longitude

                val point = LatLng(latitude, longitude)
                putPositionMarker(point, "Estás acá")
            }
        }
    }

    private fun putGeoAlertMarker(point: LatLng, title: String) : Marker{
        val markerOptions = MarkerOptions()
            .position(point)
            .title(title)
            .icon(bitmapDescriptorFromVector(applicationContext, R.mipmap.world_alert))

        moveCamera(point)

        return  mMap.addMarker(markerOptions)
    }

    private fun putPositionMarker(point: LatLng, title: String) {
        val markerOptions = MarkerOptions()
            .position(point)
            .title(title)
        currentPosMarker = mMap.addMarker(markerOptions)

        moveCamera(point)
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11f))
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
        val bitmap =
            Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    private fun moveCamera(point: LatLng) {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(point))
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Init Google Play Service
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isGrantedPermission(ACCESS_FINE_LOCATION)) {
                mMap.isMyLocationEnabled = true
            }
        } else {
            mMap.isMyLocationEnabled = true
        }

        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID

        mMap.setOnMapClickListener{
            openCreateGeoAlertPopUp(it)
        }
        // Enable Zoom Control
        mMap.uiSettings.isZoomControlsEnabled = true
        putMarkers()

    }

    private fun putMarkers(){
        this.geoAlerts = geoAlertService.findAll()
        var marker:Marker
        geoAlerts.forEach{
            marker = putGeoAlertMarker(it.point!!, it.title!!)
            markers.plus(marker)
        }
    }

    //TODO ajustar tema del dialogo para que no se muestre en toda la pantalla
    private fun openCreateGeoAlertPopUp(point: LatLng) {
        var geoAlert: GeoAlert
        val inflater = this.layoutInflater
        val popupView = inflater.inflate(R.layout.popup_geoalert, null)
        val dialogBuilder = AlertDialog.Builder(this, R.style.AppTheme_NoActionBar)

        val textPoint = "latitud: ${point.latitude} \n longitud: ${point.longitude}"
        popupView.findViewById<TextView>(R.id.pointTV).text = textPoint

        dialogBuilder
            .setView(popupView)
            .setPositiveButton(getString(R.string.ok_popup))
            { _, _ ->
                if (title.isNotEmpty()) {
                    val title = popupView.findViewById<EditText>(R.id.titleET).text.toString()
                    geoAlert = GeoAlert(title, point)
                    geoAlertService.save(geoAlert)
                    putMarkers()
                }
            }
            .setNegativeButton(getString(R.string.cancel_popup)) { _, _ -> }
            .create()
            .show()

    }
}
