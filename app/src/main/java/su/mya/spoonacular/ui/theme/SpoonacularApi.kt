package su.mya.spoonacular.ui.theme

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

data class SpoonacularResponse(
    val recipes: List<RecipesResponse>
)

data class RecipesResponse(
    val cookingMinutes: Int,
    val title: String,
    val vegetarian: String,
    val image: String,
    val veryHealthy: String,
    val pricePerServing: String


)

val SpoonacularApi by lazy {
    Retrofit.Builder()
        .baseUrl("https://api.spoonacular.com/recipes/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ISpoonacularApi::class.java)
}

interface ISpoonacularApi {
    @GET("random?apiKey=38bdc05211294b6b94799aad93dd31c0&number=100")
    suspend fun getDishes(): SpoonacularResponse
}