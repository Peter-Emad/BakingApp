package com.example.peter.bakingapp.recipe;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.peter.bakingapp.R;
import com.example.peter.bakingapp.common.base.BaseFragment;
import com.example.peter.bakingapp.common.models.dto.RecipesResponse;
import com.example.peter.bakingapp.recipe_details.RecipeDetailsActivity;

import static com.example.peter.bakingapp.common.helpers.Constants.RECIPES_GRID_LISTINGS_COLUMNS;

/**
 * Created by Peter on 03/02/2018.
 */

public class RecipesListingFragment extends BaseFragment implements RecipesListingView, RecipesListingAdapter.onItemClickListener {

    private Context context;
    private RecipesListingPresenter recipesListingPresenter;
    private ProgressBar recipesListingProgressbar;
    private RecyclerView rvRecipesListing;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recipe_listing, container, false);
        initializeViews(v);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        recipesListingPresenter = new RecipesListingPresenter(context, this);
        recipesListingPresenter.fetchRecipes();
    }

    @Override
    protected void initializeViews(View v) {
        recipesListingProgressbar = v.findViewById(R.id.recipesListingProgressbar);
        rvRecipesListing = v.findViewById(R.id.rvRecipesListing);
        if (getActivity().getResources().getBoolean(R.bool.is_landscape))
            rvRecipesListing.setLayoutManager(new GridLayoutManager(context, RECIPES_GRID_LISTINGS_COLUMNS));
        else {
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            rvRecipesListing.setLayoutManager(mLayoutManager);
        }

    }

    @Override
    protected void setListeners() {

    }

    @Override
    public void progressStatus(boolean status) {
        recipesListingProgressbar.setVisibility(status ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onRecipesLoaded(RecipesResponse[] recipesResponse) {

        RecipesListingAdapter recipesListingAdapter = new RecipesListingAdapter(context, this, recipesResponse);
        rvRecipesListing.setAdapter(recipesListingAdapter);


    }

    @Override
    public void onError(String errorMessage) {

        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRecipeClick(RecipesResponse recipesResponse) {
        RecipeDetailsActivity.startActivity(context, recipesResponse);
    }
}
