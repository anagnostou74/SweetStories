package gr.mobap.sweetstories.widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import gr.mobap.sweetstories.R;
import gr.mobap.sweetstories.models.Recipes;
import gr.mobap.sweetstories.utilities.RecipesInterface;
import gr.mobap.sweetstories.utilities.RetrofitBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressWarnings("unchecked")
public class RecipeWidgetProviderConfigureActivity extends Activity {

    private static final String PREFS_NAME = "gr.mobap.sweetstories.widget.RecipeWidgetProvider";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    private static List<Recipes> mRecipeArrayList = new ArrayList<>();
    private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private Spinner mSpinnerRecipeSelected;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = RecipeWidgetProviderConfigureActivity.this;

            int recipeSelectedPosition = mSpinnerRecipeSelected.getSelectedItemPosition();

            saveRecipePref(context, mAppWidgetId, String.valueOf(recipeSelectedPosition));
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RecipeWidgetProvider.updateAppWidgetFirstConfigure(context, appWidgetManager, mAppWidgetId, mRecipeArrayList);

            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        }
    };

    public RecipeWidgetProviderConfigureActivity() {
        super();
    }

    private static void saveRecipePref(Context context, int appWidgetId, String text) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId, text);
        prefs.apply();
    }

    static String loadRecipePref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getString(PREF_PREFIX_KEY + appWidgetId, null);
    }

    static void deleteRecipePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.recipe_widget_provider_configure);

        setResult(RESULT_CANCELED);

        RetrofitBuilder builder = new RetrofitBuilder();
        RecipesInterface recipesInterface = builder.createRecipesInterface();

        Call<List<Recipes>> call = recipesInterface.getRecipes();
        call.enqueue(new Callback<List<Recipes>>() {

            @Override
            public void onResponse(@NonNull Call<List<Recipes>> call, @NonNull Response<List<Recipes>> response) {
                if (response.isSuccessful()) {

                    mRecipeArrayList = new ArrayList<>();
                    mRecipeArrayList.addAll(response.body());

                    ArrayList<String> recipeNames = new ArrayList<>();

                    for (Recipes recipe : mRecipeArrayList) {
                        recipeNames.add(recipe.getName());
                    }

                    mSpinnerRecipeSelected = findViewById(R.id.recipes_spinner);
                    ArrayAdapter aa = new ArrayAdapter(mSpinnerRecipeSelected.getContext(), android.R.layout.simple_spinner_item, recipeNames);
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mSpinnerRecipeSelected.setAdapter(aa);

                    findViewById(R.id.add_button).setOnClickListener(mOnClickListener);

                    Intent intent = getIntent();
                    Bundle extras = intent.getExtras();
                    if (extras != null) {
                        mAppWidgetId = extras.getInt(
                                AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
                    }

                    if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
                        finish();
                    }


                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipes>> call, @NonNull Throwable t) {

            }

        });


    }
}

