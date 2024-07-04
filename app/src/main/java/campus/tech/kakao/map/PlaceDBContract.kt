package campus.tech.kakao.map

import android.provider.BaseColumns

object PlaceDBContract {
    const val DATABASE_NAME = "place.db"
    const val DATABASE_VERSION = 1

    object PlaceEntry : BaseColumns {
        const val TABLE_NAME = "places"
        const val COLUMN_PLACE_ID = "place_id"
        const val COLUMN_NAME = "name"
        const val COLUMN_TYPE = "type"
        const val COLUMN_ADDRESS = "address"
    }
}