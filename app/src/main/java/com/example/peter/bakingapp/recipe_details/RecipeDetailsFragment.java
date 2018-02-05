package com.example.peter.bakingapp.recipe_details;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.peter.bakingapp.R;
import com.example.peter.bakingapp.common.base.BaseFragment;
import com.example.peter.bakingapp.common.models.Step;
import com.example.peter.bakingapp.common.models.dto.RecipesResponse;

import java.util.List;

import static com.example.peter.bakingapp.common.helpers.Constants.RECIPE_KEY;

/**
 * Created by Peter on 04/02/2018.
 */

public class RecipeDetailsFragment extends BaseFragment{

    private Context context;
    private RecyclerView rvRecipeIngredients, rvRecipeSteps;
    private RecipesResponse recipesResponse;

    public static RecipeDetailsFragment newInstance(RecipesResponse recipesResponse) {
        RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(RECIPE_KEY, recipesResponse);
        recipeDetailsFragment.setArguments(bundle);
        return recipeDetailsFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        initializeViews(v);
        return v;
    }

    private void initRvIngredients() {
        rvRecipeIngredients.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvRecipeIngredients.setLayoutManager(mLayoutManager);
        RecipeIngredientsAdapter recipeIngredientsAdapter = new RecipeIngredientsAdapter(context, recipesResponse.getIngredients());
        rvRecipeIngredients.setAdapter(recipeIngredientsAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        initRvIngredients();
        initRvSteps();

    }

    private void initRvSteps() {
        rvRecipeSteps.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvRecipeSteps.setLayoutManager(mLayoutManager);
        RecipeStepsAdapter recipeStepsAdapter = new RecipeStepsAdapter(context, recipesResponse.getSteps());
        rvRecipeSteps.setAdapter(recipeStepsAdapter);
    }

    @Override
    protected void initializeViews(View v) {
        if (getArguments().getParcelable(RECIPE_KEY) != null)
            recipesResponse = getArguments().getParcelable(RECIPE_KEY);

        rvRecipeIngredients = v.findViewById(R.id.rvRecipeIngredients);
        rvRecipeSteps = v.findViewById(R.id.rvRecipeSteps);
        rvRecipeSteps.setNestedScrollingEnabled(false);


    }

    @Override
    protected void setListeners() {

    }


}
