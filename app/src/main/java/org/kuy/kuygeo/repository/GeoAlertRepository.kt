package org.kuy.kuygeo.repository

import android.content.ContentValues
import android.content.Context
import org.kuy.kuygeo.domain.GeoAlert
import org.kuy.kuygeo.repository.dao.DbManager
import org.kuy.kuygeo.repository.dao.GeoAlertDBConst.GeoAlertEntry.LATITUDE_COLUMN
import org.kuy.kuygeo.repository.dao.GeoAlertDBConst.GeoAlertEntry.LONGITUDE_COLUMN
import org.kuy.kuygeo.repository.dao.GeoAlertDBConst.GeoAlertEntry.TITLE_COLUMN
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
            put(TITLE_COLUMN, geoAlert?.title)
            put(LONGITUDE_COLUMN, geoAlert?.point?.longitude)
            put(LATITUDE_COLUMN, geoAlert?.point?.latitude)
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

    fun deleteAll() {
        dbManager.deleteAll()
    }
}