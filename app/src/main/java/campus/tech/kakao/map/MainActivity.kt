package campus.tech.kakao.map

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import campus.tech.kakao.map.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = SearchDbHelper(context = this)
        val wDb = db.writableDatabase

        wDb.delete(SearchData.TABLE_NAME, null, null)

        val values = ContentValues()

        for (count in 1 until 101) {
            values.put(SearchData.TABLE_COLUMN_NAME, "카페$count")
            values.put(SearchData.TABLE_COLUMN_ADDRESS, "서울 성동구 성수동 $count")
            values.put(SearchData.TABLE_COLUMN_CATEGORY, "카페")
            wDb.insert(SearchData.TABLE_NAME, null, values)

            values.put(SearchData.TABLE_COLUMN_NAME, "약국$count")
            values.put(SearchData.TABLE_COLUMN_ADDRESS, "서울 강남구 대치동 $count")
            values.put(SearchData.TABLE_COLUMN_CATEGORY, "약국")
            wDb.insert(SearchData.TABLE_NAME, null, values)

            values.clear()
        }
    }
}


