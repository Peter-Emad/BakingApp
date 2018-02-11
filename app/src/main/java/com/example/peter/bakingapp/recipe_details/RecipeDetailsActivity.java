package com.example.peter.bakingapp.recipe_details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.example.peter.bakingapp.R;
import com.example.peter.bakingapp.common.base.BaseActivity;
import com.example.peter.bakingapp.common.helpers.BakingApp;
import com.example.peter.bakingapp.common.models.Step;
import com.example.peter.bakingapp.common.models.dto.RecipesResponse;

import java.util.List;

import static com.example.peter.bakingapp.common.helpers.Constants.RECIPE_KEY;

public class RecipeDetailsActivity extends BaseActivity implements RecipeStepsAdapter.OnRecipeStepClicked {


    private RecipesResponse recipesResponse;
    private RecipeDetailsFragment recipeDetailsFragment;
    private RecipeStepDetailsFragment recipeStepDetailsFragment;


    public static void startActivity(Context context, RecipesResponse recipesResponse) {
        Intent intent = new Intent(context, RecipeDetailsActivity.class);
        intent.putExtra(RECIPE_KEY, recipesResponse);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        initializeViews();

        if (savedInstanceState != null) {
            if (getSupportFragmentManager().getFragment(savedInstanceState, RecipeStepDetailsFragment.class.getName()) != null)
                recipeStepDetailsFragment = (RecipeStepDetailsFragment) getSupportFragmentManager().getFragment(savedInstanceState, RecipeStepDetailsFragment.class.getName());
            if (getSupportFragmentManager().getFragment(savedInstanceState, RecipeDetailsFragment.class.getName()) != null)
                recipeDetailsFragment = (RecipeDetailsFragment) getSupportFragmentManager().getFragment(savedInstanceState, RecipeDetailsFragment.class.getName());
        }

        loadFragment();
    }

    @Override
    protected void initializeViews() {
        if (getIntent().hasExtra(RECIPE_KEY))
            recipesResponse = (RecipesResponse) getIntent().getExtras().get(RECIPE_KEY);
        Toolbar recipeDetailsToolbar = findViewById(R.id.recipeDetailsToolbar);
        setToolbar(recipeDetailsToolbar, recipesResponse.getName() != null ? recipesResponse.getName() : getString(R.string.app_name), true, false);
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void loadFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (recipeDetailsFragment == null)
            recipeDetailsFragment = RecipeDetailsFragment.newInstance(recipesResponse);
        if (recipeStepDetailsFragment == null)
            fragmentTransaction.replace(R.id.container_body, recipeDetailsFragment).commit();
        else
            fragmentTransaction.replace(R.id.container_body, recipeStepDetailsFragment).commit();

    }


    @Override
    public void onItemClickListener(List<Step> stepList, int position) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (recipeStepDetailsFragment == null)
            recipeStepDetailsFragment = RecipeStepDetailsFragment.newInstance(stepList, position);
        if (getResources().getBoolean(R.bool.is_tablet))
            fragmentTransaction.replace(R.id.container_body_details, recipeStepDetailsFragment).addToBackStack(null).commit();
        else
            fragmentTransaction.replace(R.id.container_body, recipeStepDetailsFragment).addToBackStack(null).commit();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (recipeDetailsFragment != null)
            getSupportFragmentManager().putFragment(outState, RecipeDetailsFragment.class.getName(), recipeDetailsFragment);
        if (recipeStepDetailsFragment != null)
            getSupportFragmentManager().putFragment(outState, RecipeStepDetailsFragment.class.getName(), recipeStepDetailsFragment);
    }

}
