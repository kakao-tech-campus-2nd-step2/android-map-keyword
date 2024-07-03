package campus.tech.kakao.map

import android.content.ContentValues
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class DataSearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_search)

        addSearchData()
    }

    private fun addSearchData(){
        val db = SearchDBHelper(context = this)
        val wDb = db.writableDatabase

        val cv = ContentValues()

        for (i in 1..10){
            cv.put(SearchDataContract.TABLE_COLUMN_NAME,"카페 $i")
            cv.put(SearchDataContract.TABLE_COLUMN_CATEGORY,"카페")
            cv.put(SearchDataContract.TABLE_COLUMN_ADDRESS,"서울 성동구 성수동 $i")

            wDb.insert(SearchDataContract.TABLE_NAME,null,cv)
        }
    }
}
