package com.example.peter.bakingapp.widget;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.peter.bakingapp.R;
import com.example.peter.bakingapp.common.helpers.BakingApp;
import com.example.peter.bakingapp.common.provider.IngredientsData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 14/02/2018.
 */

public class BakingAppRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    private int appWidgetId;
    private List<IngredientsData> ingredientsDataList = new ArrayList<>();


    public BakingAppRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }


    @Override
    public void onCreate() {
        ingredientsDataList = BakingApp.getBakingAppInstance().getIngredientsDatabase().ingredientsDao().getAll();
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

        ingredientsDataList.clear();

    }

    @Override
    public int getCount() {
            return ingredientsDataList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteView = new RemoteViews(context.getPackageName(),
                R.layout.list_view_row);

        remoteView.setTextViewText(R.id.txtIngredient, ingredientsDataList.get(position).getIngredient());

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


}
