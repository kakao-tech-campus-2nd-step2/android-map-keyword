package campus.tech.kakao.map

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkRun()

    }
    fun checkRun(){
        val checkRun = getSharedPreferences("", Context.MODE_PRIVATE)
            .getBoolean("First",true)
        if(checkRun){
            val dbHelper = SQLiteDb(this)
            insertData(dbHelper)
            getSharedPreferences("", Context.MODE_PRIVATE)
                .edit()
                .putBoolean("First", false)
                .apply()
            dbHelper.logAllData()
        }

    }
    fun insertData(dbHelper: SQLiteDb){
        for (i in 1..10) {
            val name = "카페 $i"
            val address = "서울 성동구 성수동 $i"
            val category = "카페"

            dbHelper.insertData(name, address, category)


        }
        for (i in 1..10) {
            val name = "약국 $i"
            val address = "서울 강남구 대치동 $i"
            val category = "약국"

            dbHelper.insertData(name, address, category)
        }
    }
    }

