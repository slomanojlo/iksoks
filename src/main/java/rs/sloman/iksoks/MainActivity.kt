package rs.sloman.iksoks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dagger.hilt.android.AndroidEntryPoint
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
                    LazyVerticalGridDemo(viewModel = viewModel) {
                        viewModel.play(it)
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyVerticalGridDemo(viewModel: MainViewModel, onClick: (Int) -> Unit) {

    val myList = viewModel.list.observeAsState()

    LazyVerticalGrid(
        cells = GridCells.Fixed(3),
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp,
        ),
        content = {

            myList.value?.let { list ->

                items(list.size) { index ->
                    MyButton(
                        position = index,
                        list = list
                    ) {
                        onClick(index)
                    }
                }

            }
        }
    )
}

@Composable
fun MyButton(
    position: Int,
    list: MutableList<Int>,
    onClick: (Int) -> Unit
) {
    Button(
        modifier = Modifier
            .padding(4.dp)
            .aspectRatio(1f),
        onClick = {
            onClick(position)
        },
        enabled = list[position] == 0
    ) {
        MyText(int = list[position])
    }
}

@Composable
fun MyText(
    int: Int
) {
    Text(
        text = when (int) {
            1 -> "X"
            2 -> "O"
            else -> ""
        },
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        color = MaterialTheme.colors.secondary,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(16.dp)
    )
}
