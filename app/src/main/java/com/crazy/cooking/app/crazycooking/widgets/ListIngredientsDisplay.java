package com.crazy.cooking.app.crazycooking.widgets;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.crazy.cooking.app.crazycooking.R;
import com.crazy.cooking.app.crazycooking.URLResponse;
import com.crazy.cooking.app.crazycooking.model.MyUserJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ListIngredientsDisplay extends AppCompatActivity {

    //Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_ingredients_display);
        new RecipieWidgetData().execute();
    }


    public class RecipieWidgetData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String sw = null;
            URL url = null;
            try {
                url = new URL(URLResponse.BAKING_JSON_URL);
                sw = URLResponse.getResponseFromHttp(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sw;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            widget(s);
        }
    }

    public void widget(String s){
        String[] iname;

        final MyUserJson[] myUserJsons;
        try {
            JSONArray jsonArray = new JSONArray(s);
            iname = new String[jsonArray.length()];
            myUserJsons = new MyUserJson[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                MyUserJson myUserJson = new MyUserJson(jsonObject.getInt("id"),
                        jsonObject.getString("name"),
                        jsonObject.getJSONArray("ingredients").toString(),
                        jsonObject.getJSONArray("steps").toString());
                myUserJsons[i] = myUserJson;
                iname[i] = jsonObject.getString("name");

                AlertDialog.Builder aBuilder = new AlertDialog.Builder(this);
                aBuilder.setTitle("Your Recipie");
                aBuilder.setItems(iname, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent;
                        switch (which) {
                            case 0:
                                intent = new Intent(RecipieWidget.getWidgetReciever);
                                String s1 = myUserJsons[0].getIngredientsMain();
                                intent.putExtra("WKey", s1);
                                ListIngredientsDisplay.this.sendBroadcast(intent);
                                finish();
                                break;
                            case 1:
                                intent = new Intent(RecipieWidget.getWidgetReciever);
                                String s2 = myUserJsons[1].getIngredientsMain();
                                intent.putExtra("WKey", s2);
                                ListIngredientsDisplay.this.sendBroadcast(intent);
                                finish();
                                break;
                            case 2:
                                intent = new Intent(RecipieWidget.getWidgetReciever);
                                String s3 = myUserJsons[2].getIngredientsMain();
                                intent.putExtra("WKey", s3);
                                ListIngredientsDisplay.this.sendBroadcast(intent);
                                finish();
                                break;
                            case 3:
                                intent = new Intent(RecipieWidget.getWidgetReciever);
                                String s4 = myUserJsons[3].getIngredientsMain();
                                intent.putExtra("WKey", s4);
                                ListIngredientsDisplay.this.sendBroadcast(intent);
                                finish();
                                break;

                        }
                    }
                });

                AlertDialog alertDialog = aBuilder.create();
                alertDialog.show();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
