package gr.mobap.sweetstories.ui.activities;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import gr.mobap.sweetstories.R;
import gr.mobap.sweetstories.adapters.RecipesAdapter;
import gr.mobap.sweetstories.models.Ingredients;
import gr.mobap.sweetstories.models.Recipes;
import gr.mobap.sweetstories.models.Steps;
import gr.mobap.sweetstories.utilities.Constants;
import gr.mobap.sweetstories.utilities.RecipesInterface;
import gr.mobap.sweetstories.utilities.RetrofitBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipesListActivity extends AppCompatActivity {
    @BindView(R.id.rv_main)
    RecyclerView rv_main;
    RecipesAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    List<Recipes> recipes;
    List<Steps> steps;
    List<Ingredients> ingredients;
    Parcelable layoutManagerSavedState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        recipes = new ArrayList<>();
        steps = new ArrayList<>();
        ingredients = new ArrayList<>();

        if (findViewById(R.id.relative_x_large) != null) {
            rv_main.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        } else {
            rv_main.setLayoutManager(new LinearLayoutManager(this));

        }
        if (savedInstanceState != null) {
            recipes = savedInstanceState.getParcelableArrayList(Constants.RECIPE);
            layoutManagerSavedState = savedInstanceState.getParcelable(Constants.LIST_STATE);
            adapter = new RecipesAdapter(recipes, this);
            rv_main.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            getRecipes();
        }
    }

    void initRecyclerView(List recipes) {
        adapter = new RecipesAdapter(recipes, this);
        rv_main.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    void getRecipes() {
        RetrofitBuilder builder = new RetrofitBuilder();
        RecipesInterface recipesInterface = builder.createRecipesInterface();
        Call<List<Recipes>> recipesCall = recipesInterface.getRecipes();
        recipesCall.enqueue(new Callback<List<Recipes>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipes>> call, @NonNull Response<List<Recipes>> response) {
                Log.e("connection", "success");
                if (response.body() != null) {
                    recipes = response.body();
                    initRecyclerView(recipes);

                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipes>> call, @NonNull Throwable t) {
                Log.e("connection", "failure" + " -- " + t.getMessage());

            }
        });
    }

}
