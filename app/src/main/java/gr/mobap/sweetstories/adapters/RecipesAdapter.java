package gr.mobap.sweetstories.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import gr.mobap.sweetstories.R;
import gr.mobap.sweetstories.ui.activities.DetailsActivity;
import gr.mobap.sweetstories.models.Recipes;
import gr.mobap.sweetstories.utilities.Constants;


public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {
    private final List<Recipes> recipes;
    private final Context context;

    public RecipesAdapter(List<Recipes> recipes, Context context) {
        this.recipes = recipes;
        this.context = context;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, final int position) {
        String recipeTitle = recipes.get(position).getName();
        switch (recipeTitle) {
            case "Nutella Pie":
                Glide.with(context)
                        .load(R.drawable.nutella_pie)
                        .into(holder.iv_main_pic);
                break;
            case "Brownies":
                Glide.with(context)
                        .load(R.drawable.brownies)
                        .into(holder.iv_main_pic);
                break;
            case "Yellow Cake":
                Glide.with(context)
                        .load(R.drawable.cake)
                        .into(holder.iv_main_pic);
                break;
            case "Cheesecake":
                Glide.with(context)
                        .load(R.drawable.cheesecake)
                        .into(holder.iv_main_pic);
                break;
        }

        holder.tv_main.setText(recipeTitle);
        holder.iv_main_pic.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra(Constants.RECIPE, recipes.get(position).getName());
            intent.putParcelableArrayListExtra(Constants.INGREDIENTS, (ArrayList<? extends Parcelable>) recipes.get(position).getIngredients());
            intent.putParcelableArrayListExtra(Constants.STEPS, (ArrayList<? extends Parcelable>) recipes.get(position).getSteps());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {

        if ((recipes != null) && recipes.size() > 0)
            return recipes.size();
        else
            return 0;
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_main_pic)
        ImageView iv_main_pic;
        @BindView(R.id.tv_main)
        TextView tv_main;

        RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
