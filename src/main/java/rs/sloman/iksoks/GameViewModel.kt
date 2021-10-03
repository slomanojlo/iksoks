package rs.sloman.iksoks

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import rs.sloman.iksoks.Constants.Companion.BOARD_SIZE
import javax.inject.Inject

typealias Matrix = MutableList<MutableList<Int>>

@HiltViewModel
class GameViewModel @Inject constructor() : ViewModel() {

    val _iksOks: MutableState<IksOks> = mutableStateOf(IksOks())

    init {
        setupMatrix()
    }

    fun play(position: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            val x = position / BOARD_SIZE
            val y = position % BOARD_SIZE

            val temp = IksOks(
                xPlaying = _iksOks.value.xPlaying,
                draw = _iksOks.value.draw,
                gameWon = _iksOks.value.gameWon,
                matrix = _iksOks.value.matrix
            )

            temp.play(x, y)

            withContext(Dispatchers.Main) {
                _iksOks.value = temp
            }

        }
    }

    fun setupMatrix() {
        _iksOks.value = IksOks().apply { setupMatrix() }
    }

}
