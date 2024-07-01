package campus.tech.kakao.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import campus.tech.kakao.map.data.PlaceRepository
import campus.tech.kakao.map.model.Place

class MainActivity : AppCompatActivity() {
    private lateinit var placeRepository: PlaceRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        placeRepository = PlaceRepository(this)
        placeRepository.clearAllPlaces() // 테스트용

        insertPlaces(placeRepository, "카페")
        insertPlaces(placeRepository, "식당")
    }

    private fun insertPlaces(placeRepository: PlaceRepository, category:String) {
        repeat(20) { idx ->
            placeRepository.insertPlace(Place(category + (idx + 1), "서울 성동구 성수동 ${idx + 1}", category))
        }
    }
}
