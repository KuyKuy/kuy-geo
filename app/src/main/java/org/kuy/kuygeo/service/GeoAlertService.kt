package org.kuy.kuygeo.service

import android.content.Context
import org.kuy.kuygeo.domain.GeoAlert
import org.kuy.kuygeo.repository.GeoAlertRepository

class GeoAlertService(context: Context) {
    private val noteRepository = GeoAlertRepository(context)

    fun findById(id:Long?): GeoAlert? {
        return noteRepository.findById(id)
    }

    fun findAll():ArrayList<GeoAlert>{
        return noteRepository.findAll()
    }

    fun save(geoAlert: GeoAlert?) {
        noteRepository.save(geoAlert)
    }

    fun delete(geoAlert: GeoAlert?){
        noteRepository.delete(geoAlert)
    }
}