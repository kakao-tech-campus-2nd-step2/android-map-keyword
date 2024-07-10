package campus.tech.kakao.map

interface onItemSelected {
    fun addSavedLocation(title: String)
    fun deleteSavedLocation(item: SavedLocation)
}