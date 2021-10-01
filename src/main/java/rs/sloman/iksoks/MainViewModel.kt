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
        MutableLiveData(mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0))
    val list = _list.asLiveData()

    private var isOdd = true

    fun play(index: Int) {

        val newList = _list.value?.toNewList()
        newList?.set(index, if(isOdd) 1 else 2)

        isOdd = !isOdd
        _list.value = newList
    }

    private fun getRandomNo() = Random.nextInt(0, 100)
}

fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>

fun MutableList<Int>?.toNewList() : MutableList<Int> =
    mutableListOf<Int>().apply{
        this@toNewList?.forEach {
            add(it)
        }
    }
