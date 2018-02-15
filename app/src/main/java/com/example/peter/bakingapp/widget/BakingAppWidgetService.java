package com.example.peter.bakingapp.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Peter on 14/02/2018.
 */

public class BakingAppWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new BakingAppRemoteViewsFactory(this.getApplicationContext(), intent));

    }
}
