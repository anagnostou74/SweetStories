package gr.mobap.sweetstories.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gr.mobap.sweetstories.R;
import gr.mobap.sweetstories.models.Ingredients;


public class IngredientFragment extends Fragment {
    @BindView(R.id.ingredient_card)
    CardView mIngredientCard;

    private ArrayList<Ingredients> ingredients;
    OnIngredientClickListener onIngredientClickListener;

    public IngredientFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);
        ButterKnife.bind(this, view);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        // Set the action bar back button to look like an up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        return view;
    }

    public void setIngredients(ArrayList<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    @OnClick(R.id.ingredient_card)
    void onCardClick() {
        onIngredientClickListener.onIngredientClicked(ingredients);
    }

    public interface OnIngredientClickListener {
        void onIngredientClicked(ArrayList<Ingredients> ingredients);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onIngredientClickListener = (OnIngredientClickListener) context;
        } catch (Exception e) {
            Log.e("IngredientFragment", e.getMessage());
        }
    }

}
