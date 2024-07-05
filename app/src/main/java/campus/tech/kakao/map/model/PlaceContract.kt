package campus.tech.kakao.map.model

import android.provider.BaseColumns

object PlaceContract {
    object PlaceEntry : BaseColumns {
        const val TABLE_NAME = "place"
        const val COLUMN_PLACE_NAME = "place"
        const val COLUMN_PLACE_ADDRESS = "address"
        const val COLUMN_PLACE_CATEGORY = "category"
        const val CREATE_QUERY = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${PlaceEntry.COLUMN_PLACE_NAME} varchar(30)," +
                "${PlaceEntry.COLUMN_PLACE_ADDRESS} varchar(255)," +
                "${PlaceEntry.COLUMN_PLACE_CATEGORY} varchar(30))"
        const val DROP_QUERY = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

    object SavePlaceEntry : BaseColumns {
        const val TABLE_NAME = "savePlace"
        const val COLUMN_PLACE_NAME = "savePlaceName"
        const val CREATE_QUERY = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${SavePlaceEntry.COLUMN_PLACE_NAME} varchar(30))"
        const val DROP_QUERY = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}