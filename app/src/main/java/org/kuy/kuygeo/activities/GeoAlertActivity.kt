package org.kuy.kuygeo.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.kuy.kuygeo.R
import org.kuy.kuygeo.domain.GeoAlert
import org.kuy.kuygeo.service.GeoAlertService

class GeoAlertActivity : AppCompatActivity() {

    private val geoAlertService: GeoAlertService = GeoAlertService(this)
    private var geoAlerts = ArrayList<GeoAlert>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initializeComponents()
    }

    private fun initializeComponents(){
        fab.setOnClickListener {
            goToMapActivity()
        }
        alertsRV.layoutManager = GridLayoutManager(this, 3)
        alertsRV.adapter = GeoAlertAdapter()
    }

    private fun goToMapActivity() {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
        finish()
    }

}
