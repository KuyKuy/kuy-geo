package org.kuy.kuygeo.repository.dao

import android.provider.BaseColumns
import android.provider.BaseColumns._ID
import org.kuy.kuygeo.repository.dao.GeoAlertDBConst.GeoAlertEntry.LATITUDE_COLUMN
import org.kuy.kuygeo.repository.dao.GeoAlertDBConst.GeoAlertEntry.LONGITUDE_COLUMN
import org.kuy.kuygeo.repository.dao.GeoAlertDBConst.GeoAlertEntry.TITLE_COLUMN
import org.kuy.kuygeo.repository.dao.GeoAlertDBConst.GeoAlertEntry.GEOALERT_TABLE


object GeoAlertDBConst {
    object GeoAlertEntry : BaseColumns{
        const val GEOALERT_TABLE = "geo_alert"
        const val TITLE_COLUMN = "title"
        const val LATITUDE_COLUMN = "latitude"
        const val LONGITUDE_COLUMN = "longitude"
    }

    const val SQL_CREATE_ENTRIES =
        "CREATE TABLE $GEOALERT_TABLE (" +
                "$_ID INTEGER PRIMARY KEY," +
                "$TITLE_COLUMN TEXT," +
                "$LATITUDE_COLUMN NUMERIC," +
                "$LONGITUDE_COLUMN NUMERIC)"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $GEOALERT_TABLE"

    const val ERASE_GEOALERT_TABLE = "DELETE FROM $GEOALERT_TABLE"
}