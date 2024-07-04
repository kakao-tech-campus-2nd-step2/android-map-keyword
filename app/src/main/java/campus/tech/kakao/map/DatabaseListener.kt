package campus.tech.kakao.map

interface DatabaseListener {
    fun deleteHistory(historyName: String)
    fun writeHistory(historyName: String)
}