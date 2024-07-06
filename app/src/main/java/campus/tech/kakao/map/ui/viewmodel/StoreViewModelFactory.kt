package campus.tech.kakao.map.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import campus.tech.kakao.map.data.local.StoreDao



class StoreViewModelFactory(private val storeDao: StoreDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StoreViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StoreViewModel(storeDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}