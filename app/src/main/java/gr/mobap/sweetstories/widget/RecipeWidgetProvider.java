package gr.mobap.sweetstories.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.List;

import gr.mobap.sweetstories.R;
import gr.mobap.sweetstories.models.Recipes;
import gr.mobap.sweetstories.ui.activities.RecipesListActivity;
import gr.mobap.sweetstories.utilities.Constants;
import gr.mobap.sweetstories.utilities.RecipesInterface;
import gr.mobap.sweetstories.utilities.RetrofitBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecipeWidgetProvider extends AppWidgetProvider {

    private static List<Recipes> mRecipeArrayList = new ArrayList<>();

    public static void updateAppWidgetFirstConfigure(Context context, AppWidgetManager appWidgetManager,
                                                     int appWidgetId, List<Recipes> recipeList) {

        mRecipeArrayList = recipeList;
        updateWidgetUI(context, appWidgetManager, appWidgetId);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        updateAppWidget(context, appWidgetManager, appWidgetIds);
    }

    private static void updateAppWidget(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        RetrofitBuilder builder = new RetrofitBuilder();
        RecipesInterface recipesInterface = builder.createRecipesInterface();
        Call<List<Recipes>> recipesCall = recipesInterface.getRecipes();
        recipesCall.enqueue(new Callback<List<Recipes>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipes>> call, @NonNull Response<List<Recipes>> response) {
                Log.e("connection", "success");
                if (response.body() != null) {
                    mRecipeArrayList.addAll(response.body());

                    for (int appWidgetId : appWidgetIds) {
                        updateWidgetUI(context, appWidgetManager, appWidgetId);
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Recipes>> call, @NonNull Throwable t) {
                Log.e("connection", "failure" + " -- " + t.getMessage());

            }
        });
    }

    private static void updateWidgetUI(Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId) {
        CharSequence recipeSelectedPosition = RecipeWidgetProviderConfigureActivity.loadRecipePref(context, appWidgetId);

        if (recipeSelectedPosition != null) {
            Recipes recipe = mRecipeArrayList.get(Integer.valueOf(String.valueOf(recipeSelectedPosition)));

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, RecipesListActivity.class), 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
            views.setTextViewText(R.id.widget_recipe_name, recipe.getName());
            String servingsText = String.valueOf("for " + recipe.getServings()) + " " + context.getResources().getText(R.string.portions);
            views.setTextViewText(R.id.tv_servings, servingsText);
            views.setViewVisibility(R.id.widget_recipe_layout, View.VISIBLE);
            views.setViewVisibility(R.id.widget_ingredients_list_view, View.VISIBLE);
            views.setViewVisibility(R.id.widget_iv_empty_list, View.GONE);
            views.setOnClickPendingIntent(R.id.layout_widget, pendingIntent);

            Intent serviceIntent = new Intent(context, ListWidgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

            Bundle b = new Bundle();
            b.putParcelable(Constants.RECIPE, recipe);
            serviceIntent.putExtra("BUNDLE", b);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

            views.setRemoteAdapter(R.id.widget_ingredients_list_view, serviceIntent);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

    }


    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            RecipeWidgetProviderConfigureActivity.deleteRecipePref(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }

}