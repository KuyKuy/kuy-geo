package org.kuy.kuygeo

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.gms.maps.model.LatLng
import org.kuy.kuygeo.domain.GeoAlert


class GeoAlertAdapter : RecyclerView.Adapter<AlertItemHolder>() {

    private val alerts = listOf(GeoAlert("casa", LatLng(1.toDouble(),1.toDouble())),
        GeoAlert("trabajo asdasasd asd", LatLng(1.toDouble(),1.toDouble())),
        GeoAlert("dojo", LatLng(1.toDouble(),1.toDouble())),
        GeoAlert("que cosa", LatLng(1.toDouble(),1.toDouble())))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertItemHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val item = layoutInflater.inflate(R.layout.item_alert, parent, false)
        return AlertItemHolder(item)
    }

    override fun onBindViewHolder(itemHolder: AlertItemHolder, position: Int) {
        val geoAlert = alerts[position]
        itemHolder.bindView(geoAlert)
    }


    override fun getItemCount(): Int {
        return alerts.size
    }

}