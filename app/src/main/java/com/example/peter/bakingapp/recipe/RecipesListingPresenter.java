package com.example.peter.bakingapp.recipe;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.peter.bakingapp.common.base.BasePresenter;
import com.example.peter.bakingapp.common.helpers.BakingApp;
import com.example.peter.bakingapp.common.helpers.ServicesHelper;
import com.example.peter.bakingapp.common.models.dto.RecipesResponse;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Peter on 03/02/2018.
 */

public class RecipesListingPresenter extends BasePresenter {

    private Context context;
    private RecipesListingView recipesListingView;

    public RecipesListingPresenter(Context context, RecipesListingView recipesListingView) {
        this.context = context;
        this.recipesListingView = recipesListingView;
    }

    void fetchRecipes() {
        recipesListingView.progressStatus(true);
        ServicesHelper.getInstance(context).fetchRecipes(loadRecipesSuccessListener, loadRecipesErrorListener);
    }

    private Response.Listener<JSONArray> loadRecipesSuccessListener = new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            recipesListingView.progressStatus(false);
            if (response != null) {
                RecipesResponse[] recipesResponse = BakingApp.getmGson().fromJson(response.toString(), RecipesResponse[].class);
                recipesListingView.onRecipesLoaded(recipesResponse);
            }

        }
    };

    private Response.ErrorListener loadRecipesErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            recipesListingView.progressStatus(false);
            recipesListingView.onError(error.getMessage());

        }
    };


}
