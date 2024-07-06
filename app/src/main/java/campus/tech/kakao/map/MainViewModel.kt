package campus.tech.kakao.map

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel(application: Application): AndroidViewModel(application) {
	private val placeDbHelper = PlaceDbHelper(application)
	val placeList: LiveData<MutableList<Place>> get() =  placeDbHelper._place

	private val wordDbHelper = SearchWordDbHelper(application)
	val wordList: LiveData<MutableList<SearchWord>> get() =  wordDbHelper._searchWords

	fun insertInitData(){
		if (!placeDbHelper.existData()){
			for(i in 1..10){
				placeDbHelper.addPlace(Place("카페 $i", "남양주 $i", "카페"))
				placeDbHelper.addPlace(Place("약국 $i", "남양주 $i", "약국"))
			}
		}

	}

	fun search(query: String) {
		placeDbHelper.searchPlaceName(query)
	}

	fun addWord(place: Place){
		wordDbHelper.addWord(WordfromPlace(place))
	}

	fun WordfromPlace(place: Place):SearchWord{
		return SearchWord(place.name, place.address, place.type)
	}
	fun deleteWord(word: SearchWord){
		wordDbHelper.deleteWord(word)
	}

	fun loadWord(){
		wordDbHelper.updateSearchWords()
	}
}