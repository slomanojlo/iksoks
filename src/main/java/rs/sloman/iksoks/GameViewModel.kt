package rs.sloman.iksoks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import rs.sloman.iksoks.Constants.Companion.BOARD_SIZE
import javax.inject.Inject

typealias Matrix = Array<Array<Int>>

@HiltViewModel
class GameViewModel @Inject constructor() : ViewModel() {

    private val _iksOks: MutableLiveData<IksOks> = MutableLiveData(IksOks())
    val iksOks = _iksOks.asLiveData()

    init {
        setupMatrix()
    }

    fun play(position: Int) {
        val x = position / BOARD_SIZE
        val y = position % BOARD_SIZE

        _iksOks.value?.play(x, y)
        _iksOks.notifyObserver()

    }

    fun setupMatrix() {
        _iksOks.value?.setupMatrix()
    }

}

fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>
fun <T> MutableLiveData<T>.notifyObserver() {
    this.value = this.value
}