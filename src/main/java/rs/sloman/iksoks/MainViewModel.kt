package rs.sloman.iksoks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

class MainViewModel : ViewModel() {

    private val _list : MutableLiveData<MutableList<Int>> = MutableLiveData(mutableListOf(0,0,0,0,0,0,0,0,0))
    val list = _list.asLiveData()


    fun play(index: Int) {
        _list.value?.set(index,1)
    }
}

fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>