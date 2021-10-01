package rs.sloman.iksoks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _list: MutableLiveData<MutableList<Int>> =
        MutableLiveData(mutableListOf(0, 1, 0, 0, 0, 0, 0, 0, 0))
    val list = _list.asLiveData()

    fun play(index: Int) {

        _list.value = mutableListOf(getRandomNo(),2,3,4,5,6,7,8,9)

    }

    private fun getRandomNo() = Random.nextInt(0, 100)
}

fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>