package com.example.peter.bakingapp;

import android.content.ComponentName;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.peter.bakingapp.helpers.RecyclerViewItemsCount;
import com.example.peter.bakingapp.recipe.RecipesListingActivity;
import com.example.peter.bakingapp.recipe_details.RecipeDetailsActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Peter on 17/02/2018.
 */

@RunWith(AndroidJUnit4.class)
public class RecipesListingsTest {
    @Rule
    public ActivityTestRule<RecipesListingActivity> recipesListingActivityTestRule
            = new ActivityTestRule<>(RecipesListingActivity.class);


    @Test
    public void checkIfAllRecipesAreDisplayedOnRecyclerView() {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {
        }
        onView(withId(R.id.rvRecipesListing)).check(new RecyclerViewItemsCount(4));
    }

    @Test
    public void checkIfRecipeDetailsActivityIsLaunchedOnRecipeClick() throws Exception {
        Intents.init();
        try {
            Thread.sleep(2000);
        }catch (InterruptedException ignored) {
        }
        onView(withId(android.R.id.content)).perform(ViewActions.swipeUp());
        onView(withId(R.id.rvRecipesListing))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        Thread.sleep(1000);
        intended(hasComponent(new ComponentName(getTargetContext(), RecipeDetailsActivity.class)));
        Intents.release();

    }

}
