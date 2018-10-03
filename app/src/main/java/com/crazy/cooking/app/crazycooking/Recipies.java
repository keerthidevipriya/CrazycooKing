package com.crazy.cooking.app.crazycooking;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.AsyncTaskLoader;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.crazy.cooking.app.crazycooking.model.MyUserJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Recipies extends AppCompatActivity implements android.support.v4.app.LoaderManager.LoaderCallbacks<String> {

    public static final int LaderId = 22;
    @BindView(R.id.recipies_recycler)
    RecyclerView rv;
    @BindView(R.id.progressbar_recipies)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipies);
        ButterKnife.bind(this);
        rv = findViewById(R.id.recipies_recycler);
        progressBar=findViewById(R.id.progressbar_recipies);

        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null) {
            new AlertDialog.Builder(Recipies.this)
                    .setTitle(getResources().getString(R.string.app_name))
                    .setMessage("No Internet")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }else {
            getSupportLoaderManager().initLoader(LaderId, null, this);
        }
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new android.support.v4.content.AsyncTaskLoader<String>(this) {
            @Override
            public String loadInBackground() {
                URLResponse responseConnection = new URLResponse();
                URL url = responseConnection.buildURL();
                String response = null;
                try {
                    response = responseConnection.getResponseFromHttp(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return response;
            }

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                progressBar.setVisibility(View.VISIBLE);
                forceLoad();
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        ArrayList<MyUserJson> jsonRecipieList = new ArrayList<>();
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject recipieDetail = jsonArray.getJSONObject(i);
                jsonRecipieList.add(new MyUserJson(recipieDetail.getInt("id"),
                        recipieDetail.getString("name"),recipieDetail.getJSONArray("ingredients").toString(),recipieDetail.getJSONArray("steps").toString()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        progressBar.setVisibility(View.INVISIBLE);
        rv.setLayoutManager(new GridLayoutManager(Recipies.this,colspan()));
        rv.setAdapter(new MyRecipiesList(Recipies.this, jsonRecipieList));
    }


    @Override
    public void onLoaderReset(Loader<String> loader) {

        Toast.makeText(this,"Loader in RESET state",Toast.LENGTH_SHORT).show();

    }
    public int colspan(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nCols = width/widthDivider;
        if (nCols<2){
            return 2;
        }
        return nCols;
    }

}



































