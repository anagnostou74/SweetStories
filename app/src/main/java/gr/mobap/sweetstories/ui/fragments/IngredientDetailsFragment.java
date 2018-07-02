package gr.mobap.sweetstories.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import gr.mobap.sweetstories.R;
import gr.mobap.sweetstories.adapters.IngredientsDetailsAdapter;
import gr.mobap.sweetstories.models.Ingredients;


public class IngredientDetailsFragment extends Fragment {

    @BindView(R.id.ingredient_details_rv)
    RecyclerView mIngredientDetailsRV;

    IngredientsDetailsAdapter adapter;
    ArrayList<Ingredients> ingredients;

    public IngredientDetailsFragment() {
    }

    public void setIngredients(ArrayList<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_ingredient_details, container, false);
        ButterKnife.bind(this, view);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        // Set the action bar back button to look like an up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mIngredientDetailsRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (ingredients != null)
            adapter = new IngredientsDetailsAdapter(ingredients, getActivity());
        mIngredientDetailsRV.setAdapter(adapter);

        setRetainInstance(true);

        return view;
    }



}
