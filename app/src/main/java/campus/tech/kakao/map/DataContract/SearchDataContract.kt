package campus.tech.kakao.map.DataContract

import android.provider.BaseColumns

object SearchDataContract : BaseColumns {
    const val TABLE_NAME = "SearchData"
    const val TABLE_COLUMN_NAME = "name"
    const val TABLE_COLUMN_CATEGORY = "category"
    const val TABLE_COLUMN_ADDRESS = "address"
}