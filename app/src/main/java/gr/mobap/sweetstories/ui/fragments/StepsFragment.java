package gr.mobap.sweetstories.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chootdev.recycleclick.RecycleClick;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import gr.mobap.sweetstories.R;
import gr.mobap.sweetstories.adapters.StepsListAdapter;
import gr.mobap.sweetstories.models.Steps;

import static gr.mobap.sweetstories.ui.activities.DetailsActivity.title;

public class StepsFragment extends Fragment {

    @BindView(R.id.steps_rv)
    RecyclerView mStepsRV;

    public StepsFragment() {
        setRetainInstance(true);
    }

    public void setSteps(List<Steps> steps) {
        this.steps = steps;
    }

    private List<Steps> steps;
    StepsListAdapter adapter;
    OnStepClickListener onStepClickListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_steps, container, false);
        ButterKnife.bind(this, view);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        // Set the action bar back button to look like an up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(title);
        }
        mStepsRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new StepsListAdapter(steps, getActivity());
        mStepsRV.setAdapter(adapter);

        RecycleClick.addTo(mStepsRV).setOnItemClickListener((recyclerView, position, v) -> onStepClickListener.onStepClicked(steps.get(position).getDescription(),
                steps.get(position).getVideoURL(), steps.get(position).getThumbnailURL()));
        return view;
    }

    public interface OnStepClickListener {
        void onStepClicked(String description, String video, String thumbnail);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onStepClickListener = (OnStepClickListener) context;
        } catch (Exception e) {
            Log.e("StepsFragment", e.getMessage());
        }
    }
}
