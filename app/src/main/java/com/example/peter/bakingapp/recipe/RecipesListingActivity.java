package com.example.peter.bakingapp.recipe;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.example.peter.bakingapp.R;
import com.example.peter.bakingapp.common.base.BaseActivity;

public class RecipesListingActivity extends BaseActivity {

    private RecipesListingFragment recipesListingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_listing);
        initializeViews();

        if (savedInstanceState != null)
            recipesListingFragment = (RecipesListingFragment) getSupportFragmentManager().getFragment(savedInstanceState, RecipesListingFragment.class.getName());
        else
            recipesListingFragment = new RecipesListingFragment();
        loadFragment();

    }

    @Override
    protected void initializeViews() {
        Toolbar recipeListingToolbar = findViewById(R.id.recipeListingToolbar);
        setToolbar(recipeListingToolbar, getString(R.string.app_name), false, false);
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void loadFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_body, recipesListingFragment).commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (recipesListingFragment != null)
            getSupportFragmentManager().putFragment(outState, RecipesListingFragment.class.getName(), recipesListingFragment);
    }
}


