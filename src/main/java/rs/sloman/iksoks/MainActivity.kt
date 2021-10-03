package rs.sloman.iksoks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import dagger.hilt.android.AndroidEntryPoint
import rs.sloman.iksoks.Constants.Companion.BOARD_SIZE
import rs.sloman.iksoks.ui.theme.IksOksTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            IksOksTheme {
                Surface {

                    val iksOks = remember { viewModel._iksOks }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        LazyVerticalGridDemo(
                            list = iksOks.value.matrix.flatten(),
                            gameWon = iksOks.value.gameWon
                        ) {
                            viewModel.play(it)
                        }


                        Button(
                            onClick = { viewModel.setupMatrix() },
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(text = "Reset")
                        }

                        Text(
                            text = setupMessage(iksOks),
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun setupMessage(iksOks: MutableState<IksOks>) =
        when {
            iksOks.value.gameWon -> (if (iksOks.value.xPlaying) Square.X.name else Square.O.name) + " WON!"
            iksOks.value.draw -> "DRAW!"
            else -> "Please choose a square."
        }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyVerticalGridDemo(
    list: List<Int>,
    gameWon: Boolean,
    onClick: (Int) -> Unit,
) {

    LazyVerticalGrid(
        cells = GridCells.Fixed(BOARD_SIZE),
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp,
        ),
    ) {

        items(list.size) { index ->
            MyButton(
                position = index,
                list = list,
                gameOver = gameWon,
            ) {
                onClick(index)
            }

        }
    }
}

@Composable
fun MyButton(
    position: Int,
    list: List<Int>,
    gameOver: Boolean,
    onClick: (Int) -> Unit,
) {
    Button(
        modifier = Modifier
            .padding(4.dp)
            .aspectRatio(1f),

        onClick = {
            onClick(position)
        },
        enabled = list[position] == 0 && !gameOver,
    ) {
        MyText(square = list[position])
    }
}

@Composable
fun MyText(
    square: Int,
) {
    Text(
        text = when (square) {
            1 -> Square.X.name
            2 -> Square.O.name
            else -> ""
        },
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        color = when (square) {
            1 -> MaterialTheme.colors.secondary
            2 -> MaterialTheme.colors.primary
            else -> MaterialTheme.colors.background
        },
        textAlign = TextAlign.Center,
    )
}
