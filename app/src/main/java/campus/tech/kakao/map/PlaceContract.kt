package campus.tech.kakao.map

import android.provider.BaseColumns

object PlaceContract:BaseColumns {
	const val TABLE_NAME = "place"
	const val COLUMN_NAME_NAME = "name"
	const val COLUMN_NAME_ADDRESS = "address"
	const val COLUMN_NAME_TYPE = "type"
}