package campus.tech.kakao.map

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel(application: Application): AndroidViewModel(application) {
	private var placeList = MutableLiveData<List<Place>>()
	private val dbHelper = PlaceDbHelper(application)

	fun insertInitData(){
		if (!dbHelper.existData()){
			for(i in 1..10){
				dbHelper.addPlace(Place("카페 $i", "남양주 $i", "카페"))
				dbHelper.addPlace(Place("약국 $i", "남양주 $i", "약국"))
			}
		}

	}
}