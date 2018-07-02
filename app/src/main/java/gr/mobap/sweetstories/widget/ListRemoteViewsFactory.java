package gr.mobap.sweetstories.widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import gr.mobap.sweetstories.R;
import gr.mobap.sweetstories.models.Ingredients;
import gr.mobap.sweetstories.models.Recipes;

public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context mContext;
    private final List<Ingredients> mIngredientsList;

    ListRemoteViewsFactory(Context applicationContext, Recipes recipe) {
        mContext = applicationContext;
        mIngredientsList = recipe.getIngredients();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return (mIngredientsList == null) ? 0 : mIngredientsList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        if (mIngredientsList == null || mIngredientsList.size() == 0) return null;
        final Ingredients ingredient = mIngredientsList.get(position);
        final Float ingredientQuantity = ingredient.getQuantity();
        final String ingredientMeasure = ingredient.getMeasure();
        final String ingredientName = ingredient.getIngredient();

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.recipe_widget_ingredient_item);

        views.setTextViewText(R.id.tv_quantity, String.valueOf(ingredientQuantity));
        views.setTextViewText(R.id.tv_measure, ingredientMeasure);
        views.setTextViewText(R.id.tv_ingredient, ingredientName);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
