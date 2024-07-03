package campus.tech.kakao.map.db

import android.provider.BaseColumns

object PlaceContract{
    object PlaceEntry : BaseColumns {
        const val TABLE_NAME = "PLACE"
        const val COLUMN_NAME = "NAME"
        const val COLUMN_LOCATION = "LOCATION"
        const val COLUMN_CATEGORY = "CATEGORY"
    }
}