package su.mya.spoonacular

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import kotlinx.coroutines.launch
import su.mya.spoonacular.ui.theme.RecipesResponse
import su.mya.spoonacular.ui.theme.SpoonacularApi
import su.mya.spoonacular.ui.theme.SpoonacularResponse
import su.mya.spoonacular.ui.theme.SpoonacularTheme

class MainModel : ViewModel() {
    var dishes: List<RecipesResponse>? by mutableStateOf(null)

    fun getDishes() {
        viewModelScope.launch {
            try {
                dishes = SpoonacularApi.getDishes().recipes
            } catch (e: Exception) {
                e.printStackTrace()

            }
        }
    }

    init {
        getDishes()
    }
}

@OptIn(ExperimentalCoilApi::class)
class MainActivity : ComponentActivity() {
    val model by viewModels<MainModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SpoonacularTheme {
                var i = model.dishes
                if (i == null) {
                    Text(text = "kdmcjkw")
                } else {
                    LazyVerticalGrid(columns = GridCells.Fixed(1), content = {
                        items(items = i) { item ->
                            Box(modifier = Modifier
                                .fillMaxSize()
                                .padding(20.dp)
                                .clip(RoundedCornerShape(18.dp))
                                .background(color = Color.DarkGray)
                                .clickable {
                                    val webka: Uri = Uri.parse(item.image)
                                    val inent = Intent(Intent.ACTION_VIEW, webka)
                                    if (inent.resolveActivity(packageManager) != null) {
                                        startActivity(inent)
                                    }
                                }) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize(),

                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(20.dp),
                                        color = Color.White,
                                        fontStyle = FontStyle.Italic,
                                        text = item.title,
                                        fontSize = 24.sp,
                                        textAlign = TextAlign.Center,
                                        fontFamily = FontFamily.Monospace,
                                        style = TextStyle(
                                            shadow = Shadow(
                                                color = Color.Black,
                                                offset = Offset(1.0f, 10.0f),
                                                blurRadius = 5f
                                            )
                                        )
                                    )
                                    val checkedState = remember { mutableStateOf(true) }
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                    ) {
                                        Text(modifier = Modifier.weight(3f),
                                            textAlign = TextAlign.End,
                                            text = "healthy - ${item.veryHealthy}",
                                            color = Color.White, fontFamily = FontFamily.Serif
                                        )
                                        Checkbox(modifier = Modifier.weight(2f),
                                            checked = checkedState.value,
                                            onCheckedChange = { checkedState.value = it }
                                        )


                                    }

                                    Text(
                                        text = "cookingMinutes${item.cookingMinutes}",
                                        color = Color.White, fontFamily = FontFamily.Cursive
                                    )
                                    Text(
                                        text = "price - ${item.pricePerServing}",
                                        color = Color.White, fontFamily = FontFamily.Cursive
                                    )

                                    Image(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .requiredSize(300.dp)
                                            .padding(0.dp),
                                        painter = rememberImagePainter(data = item.image),
                                        contentDescription = null,
                                    )
                                }
                            }
                        }

                    })
                }
            }
        }
    }
}

//@Composable
//fun Header(mainModel: MainModel) {
//    var i = mainModel.dishes
//    LazyVerticalGrid(columns = GridCells.Adaptive(164.dp), content = {
//        items(items = i) { item ->
//
//
//        }
//    })
//
//}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SpoonacularTheme {

    }
}