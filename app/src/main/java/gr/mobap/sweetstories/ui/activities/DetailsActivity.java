package gr.mobap.sweetstories.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import gr.mobap.sweetstories.R;
import gr.mobap.sweetstories.models.Ingredients;
import gr.mobap.sweetstories.models.Steps;
import gr.mobap.sweetstories.ui.fragments.IngredientDetailsFragment;
import gr.mobap.sweetstories.ui.fragments.IngredientFragment;
import gr.mobap.sweetstories.ui.fragments.StepDetailsFragment;
import gr.mobap.sweetstories.ui.fragments.StepsFragment;
import gr.mobap.sweetstories.utilities.Constants;

public class DetailsActivity extends AppCompatActivity
        implements IngredientFragment.OnIngredientClickListener, StepsFragment.OnStepClickListener {

    @BindView(R.id.recipe_ingredient_container)
    FrameLayout mRecipeIngredientsFrame;
    @BindView(R.id.recipe_steps_container)
    FrameLayout mRecipeStepsFrame;
    ArrayList<Steps> steps;
    ArrayList<Ingredients> ingredients;
    IngredientFragment ingredientFragment;
    StepsFragment stepsFragment;
    IngredientDetailsFragment ingredientDetailsFragment;
    StepDetailsFragment stepDetailsFragment;
    public static String title;
    public static String INGREDIENTS;
    SharedPreferences preferences;
    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        ActionBar actionBar = this.getSupportActionBar();
        // Set the action bar back button to look like an up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        preferences = getSharedPreferences(Constants.INGREDIENTS, MODE_PRIVATE);

        ingredientDetailsFragment = new IngredientDetailsFragment();
        ingredientFragment = new IngredientFragment();

        fragmentManager.beginTransaction()
                .add(R.id.recipe_ingredient_container, ingredientFragment)
                .commit();


        stepDetailsFragment = new StepDetailsFragment();
        stepsFragment = new StepsFragment();
        fragmentManager.beginTransaction()
                .add(R.id.recipe_steps_container, stepsFragment)
                .commit();

        Intent intent = this.getIntent();
        steps = intent.getParcelableArrayListExtra(Constants.STEPS);
        ingredients = intent.getParcelableArrayListExtra(Constants.INGREDIENTS);
        stepsFragment.setSteps(steps);
        ingredientFragment.setIngredients(ingredients);
        title = intent.getStringExtra(Constants.RECIPE);

        // Only create new fragments when there is no previously saved state
        if (savedInstanceState == null) {
            ingredientDetailsFragment.setIngredients(ingredients);
            fragmentManager.beginTransaction()
                    .replace(R.id.details_container, ingredientDetailsFragment)
                    .attach(ingredientDetailsFragment)
                    .commit();

        }
    }

    @Override
    public void onIngredientClicked(ArrayList<Ingredients> ingredients) {
        ingredientDetailsFragment.setIngredients(ingredients);
        fragmentManager.beginTransaction()
                .replace(R.id.details_container, ingredientDetailsFragment)
                .commit();
    }

    @Override
    public void onStepClicked(String description, String video, String thumbnail) {
        if (stepDetailsFragment != null) {
            stepDetailsFragment.setVideo(video);
            stepDetailsFragment.setDescription(description);
            fragmentManager.beginTransaction()
                    .replace(R.id.details_container, stepDetailsFragment)
                    .detach(stepDetailsFragment)
                    .attach(stepDetailsFragment)
                    .commit();
        }

    }


    public void setIngredients(List<Ingredients> ingredients) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < ingredients.size(); i++) {
            stringBuilder.append("")
                    .append(i + 1)
                    .append(". ")
                    .append(ingredients.get(i).getIngredient())
                    .append(" - ")
                    .append(ingredients.get(i).getQuantity())
                    .append(" ")
                    .append(ingredients.get(i).getMeasure())
                    .append("\n");
        }
        INGREDIENTS = stringBuilder.toString();
    }
}
