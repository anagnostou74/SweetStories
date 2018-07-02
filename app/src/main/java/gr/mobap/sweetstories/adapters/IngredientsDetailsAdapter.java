package gr.mobap.sweetstories.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import gr.mobap.sweetstories.R;
import gr.mobap.sweetstories.models.Ingredients;



public class IngredientsDetailsAdapter extends RecyclerView.Adapter<IngredientsDetailsAdapter.IngredientsViewHolder> {

    private final List<Ingredients> ingredients;
    private final Context context;

    public IngredientsDetailsAdapter(List<Ingredients> ingredients, Context context) {
        this.ingredients = ingredients;
        this.context = context;
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ingredient_item, parent, false);
        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        holder.tv_description.setText(ingredients.get(position).getIngredient());
        holder.tv_content.append(" : " + String.valueOf(ingredients.get(position).getQuantity()) + " " + ingredients.get(position).getMeasure());
    }

    @Override
    public int getItemCount() {
        if (ingredients.size() > 0)
            return ingredients.size();
        else
            return 0;
    }

    class IngredientsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_description)
        TextView tv_description;
        @BindView(R.id.tv_content)
        TextView tv_content;


        IngredientsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
