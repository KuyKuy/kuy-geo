package org.kuy.kuygeo.domain

import com.google.android.gms.maps.model.LatLng

class GeoAlert {
    var id:Long? = 0
    var title:String? = ""
    var point: LatLng? = null

    constructor(id: Long?, title: String?, point: LatLng?) {
        this.id = id
        this.title = title
        this.point = point
    }

    constructor(title: String?, point: LatLng?){
        this.title = title
        this.point = point
    }
}