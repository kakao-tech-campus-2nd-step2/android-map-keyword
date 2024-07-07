package campus.tech.kakao.map

import android.provider.BaseColumns

object StoreInfoEntry : BaseColumns {
    const val TABLE_NAME = "storeInfo"
    const val STORE_NAME = "name"
    const val STORE_LOCATION = "location"
    const val STORE_CATEGORY = "category"
}