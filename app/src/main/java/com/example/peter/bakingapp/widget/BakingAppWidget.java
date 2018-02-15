package com.example.peter.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.example.peter.bakingapp.R;
import com.example.peter.bakingapp.common.helpers.BakingApp;
import com.example.peter.bakingapp.recipe.RecipesListingActivity;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
      /*  for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }*/

        for (int appWidgetId : appWidgetIds) {
            final RemoteViews rv = new RemoteViews(context.getPackageName(),
                    R.layout.baking_app_widget);

            rv.setTextViewText(R.id.txtRecipeTitle, BakingApp.getBakingAppInstance().getIngredientsDatabase().ingredientsDao().getAll().get(0).getRecipeName());
            Intent startAppIntent = new Intent(context, RecipesListingActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, startAppIntent, 0);
            rv.setOnClickPendingIntent(R.id.rlBakingAppWidgetContainer, pendingIntent);

            Intent intent = new Intent(context, BakingAppWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    appWidgetId);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            rv.setRemoteAdapter(R.id.lvLastRecipeIngredients, intent);


            appWidgetManager.updateAppWidget(appWidgetId, rv);

        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

