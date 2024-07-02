package campus.tech.kakao.map.db

import android.provider.BaseColumns

object LocationContract : BaseColumns {
	const val TABLE_NAME = "LOCATION"
	const val COLUMN_NAME = "name"
	const val COLUMN_ADDRESS = "address"
	const val COLUMN_CATEGORY = "category"
}