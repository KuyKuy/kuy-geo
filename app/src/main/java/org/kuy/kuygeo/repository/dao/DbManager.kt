package org.kuy.kuygeo.repository.dao

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import com.google.android.gms.maps.model.LatLng
import org.kuy.kuygeo.domain.GeoAlert
import org.kuy.kuygeo.repository.dao.GeoAlertDBConst.ERASE_GEOALERT_TABLE
import org.kuy.kuygeo.repository.dao.GeoAlertDBConst.GeoAlertEntry.LATITUDE_COLUMN
import org.kuy.kuygeo.repository.dao.GeoAlertDBConst.GeoAlertEntry.LONGITUDE_COLUMN
import org.kuy.kuygeo.repository.dao.GeoAlertDBConst.GeoAlertEntry.TITLE_COLUMN
import org.kuy.kuygeo.repository.dao.GeoAlertDBConst.GeoAlertEntry.GEOALERT_TABLE


class DbManager(private val dbHelper: KuyGeoDbHelper) {

    fun insert(values: ContentValues) : Long?{
        val db = dbHelper.writableDatabase
        return db?.insert(GEOALERT_TABLE, null, values)
    }

    fun findAll(projection:Array<String>, selection:String, selectionArgs:Array<String>) : ArrayList<GeoAlert>{
        val db = dbHelper.readableDatabase
        val cursor = db?.query(GEOALERT_TABLE, projection, selection, selectionArgs, null, null, null)
        return convertToGeoAlertArray(cursor)
    }

    fun findAll() : ArrayList<GeoAlert>{
        val db = dbHelper.readableDatabase
        val cursor = db?.query(GEOALERT_TABLE, null, null, null, null, null, null)
        return convertToGeoAlertArray(cursor)
    }

    fun delete(geoAlert:GeoAlert?) : Int?{
        val db = dbHelper.writableDatabase
        val id = geoAlert?.id
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("$id")
        return db?.delete(GEOALERT_TABLE, selection, selectionArgs)
    }

    fun update(id:Long?, values:ContentValues) : Int?{
        val db = dbHelper.writableDatabase
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("$id")
        return db?.update(GEOALERT_TABLE, values, selection, selectionArgs)
    }

    fun close(){
        dbHelper.close()
    }

    private fun convertToGeoAlertArray(cursor: Cursor?): ArrayList<GeoAlert> {
        val notes = ArrayList<GeoAlert>()
        with(cursor) {
            while (this?.moveToNext()!!) {
                val id = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                val title = getString(getColumnIndexOrThrow(TITLE_COLUMN))
                val latitude = getString(getColumnIndexOrThrow(LATITUDE_COLUMN))
                val longitude = getString(getColumnIndexOrThrow(LONGITUDE_COLUMN))
                val point = LatLng(latitude.toDouble(), longitude.toDouble())
                notes.add(GeoAlert(id, title, point))
            }
        }
        return notes
    }

    fun deleteAll() {
        val db = dbHelper.writableDatabase
        db.execSQL(ERASE_GEOALERT_TABLE)
    }
}