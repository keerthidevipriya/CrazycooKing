package com.crazy.cooking.app.crazycooking.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import com.crazy.cooking.app.crazycooking.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class RecipieWidget extends AppWidgetProvider {

    public static final String getWidgetReciever = "com.crazy.cooking.app.crazycooking.CRAZY";
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.recipie_widget);
        String[] stringArray = {};
        String ingredientDisplay="Ingredients";
        if (intent.getAction()==getWidgetReciever){
            String swidgetData = intent.getStringExtra("WKey");
            try {
                JSONArray jsonArray = new JSONArray(swidgetData);
                stringArray = new String [jsonArray.length()];
                for (int i=0; i <jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    stringArray[i] = jsonObject.getString("ingredient") + " " +jsonObject.getString("quantity") + " " + jsonObject.getString("measure") + "\n";
                    ingredientDisplay+=jsonObject.getString("ingredient") + " " +jsonObject.getString("quantity") + " " + jsonObject.getString("measure") + "\n";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            remoteViews.setViewVisibility(R.id.id_widbtn,View.GONE);
            Intent recipieIntent = new Intent(context,MyService.class);
            Bundle bundle = new Bundle();
            bundle.putInt(AppWidgetManager.EXTRA_APPWIDGET_ID,remoteViews.getLayoutId());
            bundle.putStringArray("BundleKey",stringArray);
            recipieIntent.putExtras(bundle);
            Uri buildUri = Uri.parse(recipieIntent.toUri(Intent.URI_INTENT_SCHEME));
            recipieIntent.setData(buildUri);
            remoteViews.setRemoteAdapter(R.id.id_widtextlist,recipieIntent);
        }
        AppWidgetManager.getInstance(context).updateAppWidget(new ComponentName(context,RecipieWidget.class),remoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //for(int i = 0;i<appWidgetIds.length;i++){
        for (int i:appWidgetIds){
            Intent intent = new Intent(context,ListIngredientsDisplay.class);
            PendingIntent pi = PendingIntent.getActivity(context,0,intent,0);
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.recipie_widget);
            remoteViews.setOnClickPendingIntent(R.id.id_widbtn,pi);
            appWidgetManager.updateAppWidget(i,remoteViews);
        }
    }
}

