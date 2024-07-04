package campus.tech.kakao.map

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MapViewModel(mContext: Context) : ViewModel() {
    private val model = MapModel(mContext)

    fun insertLocation(location: Location) {
        model.insertLocation(location)
    }

    fun searchLocation(locName: String, isExactMatch: Boolean): List<Location> {
        return model.searchLocation(locName, isExactMatch)
    }

    fun writeHistory(name: String) {
        model.writeHistory(name)
    }
    fun getAllLocation(): List<Location> {
        return model.getAllLocation()
    }
}