package org.kuy.kuygeo

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initializeComponents()
    }

    private fun initializeComponents(){
        fab.setOnClickListener { view ->
            goToMapActivity()
        }
        alertsRV.layoutManager = LinearLayoutManager(this)
        alertsRV.adapter = AlertAdapter()
    }

    private fun goToMapActivity() {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
        finish()
    }

}
