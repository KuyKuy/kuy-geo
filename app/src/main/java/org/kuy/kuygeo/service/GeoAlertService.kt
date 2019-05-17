package org.kuy.kuygeo.service

import android.content.Context
import org.kuy.kuygeo.domain.GeoAlert
import org.kuy.kuygeo.repository.GeoAlertRepository

class GeoAlertService(context: Context) {
    private val geoAlertRepository = GeoAlertRepository(context)

    fun findById(id:Long?): GeoAlert? {
        return geoAlertRepository.findById(id)
    }

    fun findAll():ArrayList<GeoAlert>{
        return geoAlertRepository.findAll()
    }

    fun save(geoAlert: GeoAlert?) {
        geoAlertRepository.save(geoAlert)
    }

    fun delete(geoAlert: GeoAlert?){
        geoAlertRepository.delete(geoAlert)
    }

    fun deleteAll(){
        geoAlertRepository.deleteAll()
    }
}