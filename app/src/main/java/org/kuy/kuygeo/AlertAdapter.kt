package org.kuy.kuygeo

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_alert.view.*


class AlertAdapter : RecyclerView.Adapter<AlertItemHolder>() {

    val alerts = listOf("dos","hola", "perra", "grom")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertItemHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val item = layoutInflater.inflate(R.layout.item_alert, parent, false)
        return AlertItemHolder(item)
    }

    override fun onBindViewHolder(itemHolder: AlertItemHolder, position: Int) {
        val title = alerts[position]
        itemHolder.view.alertItemTitle.text = title
    }


    override fun getItemCount(): Int {
        return alerts.size
    }

}