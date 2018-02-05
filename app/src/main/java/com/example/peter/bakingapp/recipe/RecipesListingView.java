package com.example.peter.bakingapp.recipe;

import com.example.peter.bakingapp.common.base.BaseView;
import com.example.peter.bakingapp.common.models.dto.RecipesResponse;

/**
 * Created by Peter on 03/02/2018.
 */

interface RecipesListingView extends BaseView {
    void progressStatus(boolean status);

    void onRecipesLoaded(RecipesResponse[] recipesResponse);

    void onError(String errorMessage);
}
