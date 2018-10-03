package com.crazy.cooking.app.crazycooking.widgets;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.crazy.cooking.app.crazycooking.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyService extends RemoteViewsService {
    public MyService() {
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new WidgetView(this.getApplicationContext(),intent));
    }

    public class WidgetView implements RemoteViewsService.RemoteViewsFactory{
        String[] atringArrayIngredient;
        int ingrewidid;
        Context context;
        List<String> ingreWidList = new ArrayList<String>();

        public WidgetView(Context context,Intent intent) {
            Bundle bundle = intent.getExtras();
            this.context = context;
            if (!bundle.isEmpty()){
                ingrewidid = bundle.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID);
                atringArrayIngredient = bundle.getStringArray("BundleKey");
            }
        }

        @Override
        public void onCreate() {
            onUpdateWidgetListView();
        }

        @Override
        public void onDataSetChanged() {
            onUpdateWidgetListView();
        }

        @Override
        public void onDestroy() {
            ingreWidList.clear();
        }

        @Override
        public int getCount() {
            return ingreWidList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.custom_dialog_activity);
            remoteViews.setTextViewText(R.id.text_view,ingreWidList.get(position));
            return remoteViews;
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

        public void onUpdateWidgetListView(){
            List<String> widlist = new ArrayList<String>(Arrays.asList(atringArrayIngredient));
            this.ingreWidList = widlist;
        }
    }


}
