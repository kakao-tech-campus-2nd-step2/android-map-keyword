package campus.tech.kakao.map.DataContract

import android.provider.BaseColumns

object RecentDataContract : BaseColumns {
    const val TABLE_NAME = "RecentData"
    const val TABLE_COLUMN_NAME = "name"
    const val TABLE_COLUMN_TIME = "recentSearchTime"
}