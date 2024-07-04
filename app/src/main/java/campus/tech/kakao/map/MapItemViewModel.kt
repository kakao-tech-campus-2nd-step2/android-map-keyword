package campus.tech.kakao.map

import android.content.Context
import androidx.lifecycle.ViewModel

class MapItemViewModel(context: Context) : ViewModel(){
    private var mapItemList : MutableList<MapItem>
    val mapDB = MapItemDbHelper(context)

    init {
        mapItemList = mapDB.makeMapItemList()
    }

    fun getMapItemList() : MutableList<MapItem>{
        //mapItemList = mapDB.searchMapItem(category)
        return mapItemList
    }


}