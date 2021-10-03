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
import androidx.compose.runtime.livedata.observeAsState
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

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            IksOksTheme {
                // A surface container using the 'background' color from the theme
                Surface {

                    val gameWon = viewModel.gameWon.observeAsState()
                    val message = viewModel.message.observeAsState()

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        LazyVerticalGridDemo(
                            list = viewModel.list,
                            gameWon = gameWon.value == true
                        ) {
                            viewModel.play(it)
                        }

                        message.value?.let {
                            Text(
                                text = it,
                                modifier = Modifier.padding(16.dp)
                            )
                        }

                        Button(
                            onClick = { viewModel.setupMatrix() },
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(text = "Reset")
                        }

                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyVerticalGridDemo(
    list: LiveData<MutableList<Int>>,
    gameWon: Boolean,
    onClick: (Int) -> Unit
) {

    val myList = list.observeAsState()

    LazyVerticalGrid(
        cells = GridCells.Fixed(BOARD_SIZE),
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp,
        ),
    ) {
        myList.value?.let { list ->

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
}

@Composable
fun MyButton(
    position: Int,
    list: List<Int>,
    gameOver: Boolean,
    onClick: (Int) -> Unit
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
    square: Int
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
