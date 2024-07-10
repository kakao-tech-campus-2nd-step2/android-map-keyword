package campus.tech.kakao.map

interface onItemSelected {
    fun insertSavedLocation(title: String)
    fun deleteSavedLocation(item: SavedLocation)
}