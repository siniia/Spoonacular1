package su.mya.spoonacular

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
                    LazyVerticalGrid(columns = GridCells.Adaptive(164.dp), content = {
                        items(items = i) { item ->
                            Box(modifier = Modifier.fillMaxSize()) {
                            }
                            Text(text = item.title)
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