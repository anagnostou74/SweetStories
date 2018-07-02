package gr.mobap.sweetstories.utilities;

import java.util.List;

import gr.mobap.sweetstories.models.Recipes;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipesInterface {
    @GET("baking.json")
    Call<List<Recipes>> getRecipes();

}
