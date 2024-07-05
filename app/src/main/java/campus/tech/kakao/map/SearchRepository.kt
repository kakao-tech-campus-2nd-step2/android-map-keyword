package campus.tech.kakao.map

class SearchRepository() {
    private val preferenceManager = MyApplication.prefs

    fun getSearchHistory(): ArrayList<SearchHistory> {
        return preferenceManager.getArrayList(Constants.SEARCH_HISTORY_KEY)
    }

    fun saveSearchHistory(searchHistory: SearchHistory) {
        val currentList = getSearchHistory()
        preferenceManager.savePreference(Constants.SEARCH_HISTORY_KEY, searchHistory, currentList)
    }

    fun deleteSearchHistory(position: Int) {
        val currentList = getSearchHistory()
        preferenceManager.deleteArrayListItem(Constants.SEARCH_HISTORY_KEY, position)
    }
}