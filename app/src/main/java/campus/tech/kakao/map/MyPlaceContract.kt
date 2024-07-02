package campus.tech.kakao.map

import android.provider.BaseColumns

object MyPlaceContract {
    object Place : BaseColumns {
        const val TABLE_NAME = "place"
        const val COLUMN_NAME = "name"
        const val COLUMN_IMG = "img"
        const val COLUMN_LOCATION = "location"
        const val COLUMN_CATEGORY = "category"
    }

    object MyResearch : BaseColumns {
        const val TABLE_NAME = "myresearch"
        const val COLUMN_NAME = "name"
        const val COLUMN_IMG = "img"
        const val COLUMN_LOCATION = "location"
        const val COLUMN_CATEGORY = "category"
    }

}