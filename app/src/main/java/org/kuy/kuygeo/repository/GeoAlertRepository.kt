package org.kuy.kuygeo.repository

import android.content.ContentValues
import android.content.Context
import org.kuy.kuygeo.domain.GeoAlert
import org.kuy.kuygeo.repository.dao.DbManager
import org.kuy.kuygeo.repository.dao.GeoAlertDBConst.GeoAlertEntry.COLUMN_LATITUDE
import org.kuy.kuygeo.repository.dao.GeoAlertDBConst.GeoAlertEntry.COLUMN_LONGITUDE
import org.kuy.kuygeo.repository.dao.GeoAlertDBConst.GeoAlertEntry.COLUMN_TITLE
import org.kuy.kuygeo.repository.dao.KuyGeoDbHelper

class GeoAlertRepository(context: Context) {
    private val kuyGeoDbHelper = KuyGeoDbHelper(context)
    private val dbManager = DbManager(kuyGeoDbHelper)
    private var geoAlerts = ArrayList<GeoAlert>()

    fun findAll():ArrayList<GeoAlert>{
        geoAlerts = dbManager.findAll()
        return geoAlerts
    }

    fun findById(id: Long?): GeoAlert? {
        findAll()
        return geoAlerts.find { it.id == id }
    }

    fun save(geoAlert: GeoAlert?) {
        val noteFounded = findById(geoAlert?.id)
        val values = ContentValues().apply {
            put(COLUMN_TITLE, geoAlert?.title)
            put(COLUMN_LONGITUDE, geoAlert?.point?.longitude)
            put(COLUMN_LATITUDE, geoAlert?.point?.latitude)
        }
        if(noteFounded != null){
            dbManager.update(geoAlert?.id, values)
        }
        else{
            dbManager.insert(values)
        }
        dbManager.close()
    }

    fun delete(geoAlert: GeoAlert?){
        dbManager.delete(geoAlert)
        dbManager.close()
    }
}