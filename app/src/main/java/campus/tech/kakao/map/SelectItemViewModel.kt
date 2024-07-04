package campus.tech.kakao.map

import android.content.Context
import androidx.lifecycle.ViewModel

class SelectItemViewModel(context: Context) : ViewModel(){
    private var selectItemList : MutableList<MapItem>
    val selectItemDB = SelectItemDBHelper(context)

    init {
        //selectItemDB.insertSelectItem("카페1", "서울 성동구 성수동", "카페", 1)
        selectItemList = selectItemDB.makeAllSelectItemList()
    }

    fun updateSelectItemList() {
        selectItemDB.onUpgrade(selectItemDB.writableDatabase, 1, 2)
    }

    fun getSelectItemList() : MutableList<MapItem>{
        return selectItemList
    }
}