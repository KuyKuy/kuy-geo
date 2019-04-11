package org.kuy.kuygeo.repository.dao

import android.provider.BaseColumns
import android.provider.BaseColumns._ID
import org.kuy.kuygeo.repository.dao.GeoAlertDBConst.GeoAlertEntry.COLUMN_LATITUDE
import org.kuy.kuygeo.repository.dao.GeoAlertDBConst.GeoAlertEntry.COLUMN_LONGITUDE
import org.kuy.kuygeo.repository.dao.GeoAlertDBConst.GeoAlertEntry.COLUMN_TITLE
import org.kuy.kuygeo.repository.dao.GeoAlertDBConst.GeoAlertEntry.TABLE_NAME


object GeoAlertDBConst {
    object GeoAlertEntry : BaseColumns{
        const val TABLE_NAME = "geo_alert"
        const val COLUMN_TITLE = "title"
        const val COLUMN_LATITUDE = "latitude"
        const val COLUMN_LONGITUDE = "longitude"
    }

    const val SQL_CREATE_ENTRIES =
        "CREATE TABLE $TABLE_NAME (" +
                "$_ID INTEGER PRIMARY KEY," +
                "$COLUMN_TITLE TEXT," +
                "$COLUMN_LATITUDE NUMERIC," +
                "$COLUMN_LONGITUDE NUMERIC)"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
}