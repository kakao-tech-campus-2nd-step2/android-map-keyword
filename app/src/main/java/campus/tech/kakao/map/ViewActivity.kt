package campus.tech.kakao.map

import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

class ViewActivity : AppCompatActivity() {
    private lateinit var viewModel: LocationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = LocationViewModel.getInstance(Repository(this))

        viewModel.locations.observe(this, Observer { locations ->
            Log.d("sqlite33","$locations")
        })

        test2()
        
    }

    private fun test1(){
        // 초기화
        viewModel.dropTable()

        // 입력
        viewModel.insert(Location("컴포즈a","부산시 금정구", "카페"))
        viewModel.insert(Location("컴포즈b", "부산시 금정구", "카페"))
        viewModel.insert(Location("컴포즈c", "서울시 강남구", "카페"))

        // 업데이트
        viewModel.update(Location("컴포즈a", "서울시 해운대구", "카페"))

        // 삭제
        viewModel.delete("컴포즈b")

        viewModel.fetch()

        // 예상결과
        /* 컴포즈a         부산시 금정구         카페
           컴포즈c         부산시 금정구         카페
        */
    }

    private fun test2(){
        // 초기화
        viewModel.dropTable()

        // 입력
        for(i in 0..100){
            viewModel.insert(Location("컴포즈"+i,"부산시 금정구"+i, "카페"))
        }

    }

    override fun onRestart() {
        super.onRestart()
        test1()
    }

}
