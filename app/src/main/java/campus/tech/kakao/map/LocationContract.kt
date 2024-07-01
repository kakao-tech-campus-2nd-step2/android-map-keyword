package campus.tech.kakao.map

enum class Type{
    NONE ,ETC, CINEMA, MARKET, HOSPITAL
}
object Location{
    const val TABLE_NAME: String = "DB_Location"
    const val COLUMN_NAME: String = "name"
    const val COLUMN_LOCATION: String = "location"
    val TYPE: Type = Type.NONE
}