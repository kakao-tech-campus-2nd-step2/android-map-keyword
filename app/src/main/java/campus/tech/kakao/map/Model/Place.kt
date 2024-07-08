package campus.tech.kakao.map.Model

import campus.tech.kakao.map.Util.PlaceCategory

data class Place(val name: String, var address: String? = null, var category: PlaceCategory? = null)
