package org.kuy.kuygeo

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.item_alert.view.*
import org.kuy.kuygeo.domain.GeoAlert

class AlertItemHolder(val view: View) : RecyclerView.ViewHolder(view) {

    fun bindView(geoAlert: GeoAlert){
        view.alertItemTitle.text = geoAlert.title
    }
}