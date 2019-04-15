package org.kuy.kuygeo.repository.dao

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import com.google.android.gms.maps.model.LatLng
import org.kuy.kuygeo.domain.GeoAlert
import org.kuy.kuygeo.repository.dao.GeoAlertDBConst.GeoAlertEntry.COLUMN_LATITUDE
import org.kuy.kuygeo.repository.dao.GeoAlertDBConst.GeoAlertEntry.COLUMN_LONGITUDE
import org.kuy.kuygeo.repository.dao.GeoAlertDBConst.GeoAlertEntry.COLUMN_TITLE
import org.kuy.kuygeo.repository.dao.GeoAlertDBConst.GeoAlertEntry.TABLE_NAME


class DbManager(private val dbHelper: KuyGeoDbHelper) {

    fun insert(values: ContentValues) : Long?{
        val db = dbHelper.writableDatabase
        return db?.insert(TABLE_NAME, null, values)
    }

    fun findAll(projection:Array<String>, selection:String, selectionArgs:Array<String>) : ArrayList<GeoAlert>{
        val db = dbHelper.readableDatabase
        val cursor = db?.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null)
        return toGeoAlertArray(cursor)
    }

    fun findAll() : ArrayList<GeoAlert>{
        val db = dbHelper.readableDatabase
        val cursor = db?.query(TABLE_NAME, null, null, null, null, null, null)
        return toGeoAlertArray(cursor)
    }

    fun delete(geoAlert:GeoAlert?) : Int?{
        val db = dbHelper.writableDatabase
        val id = geoAlert?.id
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("$id")
        return db?.delete(TABLE_NAME, selection, selectionArgs)
    }

    fun update(id:Long?, values:ContentValues) : Int?{
        val db = dbHelper.writableDatabase
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("$id")
        return db?.update(TABLE_NAME, values, selection, selectionArgs)
    }

    fun close(){
        dbHelper.close()
    }

    private fun toGeoAlertArray(cursor: Cursor?): ArrayList<GeoAlert> {
        val notes = ArrayList<GeoAlert>()
        with(cursor) {
            while (this?.moveToNext()!!) {
                val id = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                val title = getString(getColumnIndexOrThrow(COLUMN_TITLE))
                val latitude = getString(getColumnIndexOrThrow(COLUMN_LATITUDE))
                val longitude = getString(getColumnIndexOrThrow(COLUMN_LONGITUDE))
                val point = LatLng(latitude.toDouble(), longitude.toDouble())
                notes.add(GeoAlert(id, title, point))
            }
        }
        return notes
    }
}