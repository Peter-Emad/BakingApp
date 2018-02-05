package com.example.peter.bakingapp.recipe_details;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.peter.bakingapp.R;
import com.example.peter.bakingapp.common.base.BaseFragment;
import com.example.peter.bakingapp.common.helpers.Constants;
import com.example.peter.bakingapp.common.models.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 05/02/2018.
 */

public class RecipeStepDetailsFragment extends BaseFragment {


    private Context context;
    private List<Step> steps;
    private int selectedPosition = -1;

    public static RecipeStepDetailsFragment newInstance(List<Step> stepList, int position) {
        RecipeStepDetailsFragment recipeStepDetailsFragment = new RecipeStepDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.RECIPE_STEP_SELECTED_POSITION, position);
        bundle.putParcelableArrayList(Constants.RECIPE_STEPS_KEY, (ArrayList<? extends Parcelable>) stepList);
        recipeStepDetailsFragment.setArguments(bundle);
        return recipeStepDetailsFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            steps = bundle.getParcelableArrayList(Constants.RECIPE_STEPS_KEY);
            selectedPosition = bundle.getInt(Constants.RECIPE_STEP_SELECTED_POSITION);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        initializeViews(v);
        setListeners();
        return v;
    }

    @Override
    protected void initializeViews(View v) {

    }

    @Override
    protected void setListeners() {

    }
}
