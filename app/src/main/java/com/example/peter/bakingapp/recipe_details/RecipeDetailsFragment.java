package com.example.peter.bakingapp.recipe_details;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.peter.bakingapp.R;
import com.example.peter.bakingapp.common.base.BaseFragment;
import com.example.peter.bakingapp.common.helpers.BakingApp;
import com.example.peter.bakingapp.common.models.dto.RecipesResponse;
import com.example.peter.bakingapp.common.provider.IngredientsData;

import java.util.ArrayList;
import java.util.List;

import static com.example.peter.bakingapp.common.helpers.Constants.RECIPE_KEY;

/**
 * Created by Peter on 04/02/2018.
 */

public class RecipeDetailsFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor> {

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
        // run the sentence in a new thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                addLatestIngredientsToDatabase();
            }
        }).start();
    }

    private void addLatestIngredientsToDatabase() {
        // clear database of last ingredients
        if (BakingApp.getBakingAppInstance().getIngredientsDatabase().ingredientsDao().getAll().size() > 0)
            BakingApp.getBakingAppInstance().getIngredientsDatabase().ingredientsDao().deleteAll(BakingApp.getBakingAppInstance().getIngredientsDatabase().ingredientsDao().getAll());
        List<IngredientsData> ingredientsDataList = new ArrayList<>();
        for (int i = 0; i < recipesResponse.getIngredients().size(); i++) {
            IngredientsData ingredientsData = new IngredientsData();
            ingredientsData.setIngredient(recipesResponse.getIngredients().get(i).getIngredient());
            ingredientsData.setMeasure(recipesResponse.getIngredients().get(i).getMeasure());
            ingredientsData.setQuantity(recipesResponse.getIngredients().get(i).getQuantity());
            ingredientsDataList.add(ingredientsData);
        }
        if (ingredientsDataList.size() > 0)
            BakingApp.getBakingAppInstance().getIngredientsDatabase().ingredientsDao().insertAll(ingredientsDataList);
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


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
