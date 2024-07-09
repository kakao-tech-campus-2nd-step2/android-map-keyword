import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    /**
     *  코틀린에서 사용하는 companion object는 클래스 내부에 포함된 정적 멤버 변수와 메서드를 선언할 때 사용
     */
    companion object {
        private const val DATABASE_NAME = "mydatabase.db"
        private const val DATABASE_VERSION = 1
    }

    // 테이블 생성 등 초기 데이터베이스 설정
    override fun onCreate(p0: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    // 데이터베이스 버전이 변경될 때 실행되는 업그레이드 로직
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    // 데이터 추가
    fun insertData() {
        TODO("Not yet implemented")
    }

    // 데이터 전체 조회
    fun getAllData() {
        TODO("Not yet implemented")
    }
}